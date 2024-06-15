package pegas.controller.clientController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.dto.userdto.LoginDTO;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.ReserveProduct;
import pegas.entity.Role;
import pegas.entity.User;
import pegas.service.clientService.ClientService;
import pegas.service.integration.FileGateWay;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/users")
public class ClientController {

    private final ClientService clientService;
    private final FileGateWay fileGateWay;

    /**
     * makes the list of roles available to all controllers
     * @return List<Role>
     */

    @ModelAttribute("roles")
    public List<Role> getRole(){
        return Arrays.asList(Role.values());
    }

    /**
     * start page for all urls that are forbidden by spring security
     * @return html page of login
     */
    @GetMapping("/login")
    public String start(){
        return "login";
    }

    /**
     * Successful registration and authorization are transferred here,
     * and query parameter point id is assigned for further tracking of actions
     * @param model the standard java parameter
     * @param currentUser after registration/authorization, receive UserDetails
     * @return redirect to storage page
     */
    @GetMapping("/income")
    public String authorisation(Model model, @AuthenticationPrincipal UserDetails currentUser){
        User user = clientService.findByUsername(currentUser.getUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not found"));
        return "redirect:/v3/storage?id="+user.getId();
    }

    /**
     * the controller is used from storageController
     * @param id of user
     * @return user's personal account as user.html;
     */
    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id){
        clientService.findById(id).map(i-> model.addAttribute("user", i))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not found"));
        List<ReserveProduct> list = clientService.findById(id).map(ReadUserDTO::getReserve).orElse(null);
        assert list != null;
        model.addAttribute("count", list.stream().map(ReserveProduct::getAmount).reduce(0, Integer::sum));
        return "user";
    }

    /**
     * the standard CRUD operation update
     * @param update
     * @param id of user
     * @param bindingResult collects errors from @Validated
     * @param redirectAttributes redirects with the object data for error handling
     * @return redirect to a personal account
     */
    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") @Validated CreateUpdateUserDTO update, @PathVariable("id") Long id,
    BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", update);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v3/users/{id}";
        }
        ReadUserDTO read = clientService.update(update, id);
        fileGateWay.writeToFile("updateUser.txt",read.toString());
        return "redirect:/v3/users/"+read.getId();
    }

    /**
     * the standard CRUD operation create
     * @param create
     * @param bindingResult collects errors from @Validated
     * @param redirectAttributes redirects with the object data for error handling
     * @return redirect to storage
     */
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Validated CreateUpdateUserDTO create,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", create);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v3/users/registration";
        }
        ReadUserDTO read = clientService.create(create);
        fileGateWay.writeToFile("createUser.txt",read.toString());
        return "redirect:/v3/storage?id="+read.getId();
    }

    /**
     * password encryption is not enabled, so everyone's password is the same. Further work is required
     * @param model the standard java object
     * @param create create person after registration
     * @return html page registration.html;
     */
    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") CreateUpdateUserDTO create){
        model.addAttribute("user", create);
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    /**
     * CRUD operation delete
     * @param id of user
     * @return to login/registration page
     */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id){
        if(!clientService.deleteUser(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        fileGateWay.writeToFile("deleteUser.txt",id.toString());
        return "redirect:/v3/users";
    }

}
