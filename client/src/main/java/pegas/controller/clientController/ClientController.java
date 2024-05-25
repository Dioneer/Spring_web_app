package pegas.controller.clientController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.Role;
import pegas.service.clientService.ClientService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("v3/users")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id){
        clientService.findById(id).map(i-> model.addAttribute("user", i))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Validated CreateUpdateUserDTO update, @PathVariable("id") Long id,
    BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", update);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/users/"+id;
        }
        return "asd";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Validated CreateUpdateUserDTO create,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", create);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/users/registration";
        }
        ReadUserDTO read = clientService.create(create);
        return "redirect:/v3/users/"+read.getFirstname();
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") CreateUpdateUserDTO create){
        model.addAttribute("user", create);
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/delete")
    public String deleteUser(@PathVariable("id") Long id){
        if(!clientService.deleteUser(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/v3/users/"+id;
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception exception, HttpServletRequest request){
        log.error(request.getContextPath() + "failed request", exception);
        return "error";
    }

}
