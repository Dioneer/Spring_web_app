package pegas.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.*;
import pegas.service.StorageService;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v4")
public class StorageAdminController{

    private final StorageService storageService;
    private final OAuth2AuthorizedClientManager authorizedClientManager;


    @GetMapping
    public String findAll(Model model){
        model.addAttribute("products", storageService.getAll());
        return "all";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("products", storageService.getById(id));
        return "admin";
    }

    @GetMapping("/filter")
    public String findAll(Model model, @ModelAttribute ProductFilter productFilter){
        model.addAttribute("products", storageService.getAllByFilter(productFilter));
        model.addAttribute("filter", productFilter);
        return "all";
    }

    @GetMapping("/startCreate")
    public String create(Model model, @ModelAttribute("products") CreateEditProductDTO create){
        model.addAttribute("products", create);
        return "create";
    }
    @GetMapping("/{id}/startUpdate")
    public String update(Model model, @PathVariable Long id, @ModelAttribute("products") ReadProductDTO readProductDTO){
        return "redirect:/v4/"+id;
    }
    @PostMapping(value = "/create")
    public String create(@ModelAttribute CreateEditProductDTO create, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("products", create);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v4/startCreate";
        }
        uploadImage(create.getProductImage());
        SendDTO sendDTO = new SendDTO(create.getProductMark(), create.getProductModel(),create.getPrice(),
                create.getAmount(),create.getReserved(),create.getProductImage().getOriginalFilename());
        storageService.create(sendDTO);
        return "redirect:/v4";
    }
    @SneakyThrows
    private void uploadImage(MultipartFile productImage) {
        if(!productImage.isEmpty()) {
            storageService.upload(productImage.getOriginalFilename(), productImage.getInputStream());
        }
    }
    @PostMapping(value = "/{id}/update")
    public String update(@ModelAttribute @Validated CreateEditProductDTO update, @PathVariable Long id,
                         BindingResult bindingResult,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("products", update);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v4/"+id+"/startUpdate";
        }
            ReadProductDTO readProductDTO = storageService.getById(id);
        if (readProductDTO.getProductImage().equals(update.getProductImage().getOriginalFilename()) ||
                update.getProductImage().getOriginalFilename().isEmpty()){
            uploadImage(update.getProductImage());
            SendDTO sendDTO = new SendDTO(update.getProductMark(), update.getProductModel(),update.getPrice(),
                    update.getAmount(),update.getReserved(),readProductDTO.getProductImage());
            storageService.update(id, sendDTO);
        }else{
            uploadImage(update.getProductImage());
            SendDTO sendDTO = new SendDTO(update.getProductMark(), update.getProductModel(),update.getPrice(),
                    update.getAmount(),update.getReserved(),update.getProductImage().getOriginalFilename());
            storageService.update(id, sendDTO);
        }
        return "redirect:/v4/"+id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        if(!storageService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/v4";
    }

    @PostMapping("/{id}/reservation")
    public String reservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @SessionAttribute("userId") Long userId){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v4/"+id;
        }
        storageService.reservation(orderDTO, id);
        return "redirect:/v4/"+id;
    }

    @PostMapping("/{id}/unreservation")
    public String unReservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v4/"+id;
        }
        storageService.unReservation(orderDTO, id);
        return "redirect:/v4/"+id;
    }

}

