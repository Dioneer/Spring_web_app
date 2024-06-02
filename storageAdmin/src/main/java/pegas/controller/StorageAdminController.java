package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.OrderDTO;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;
import pegas.service.StorageService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/v4")
public class StorageAdminController{
    private final StorageService storageService;

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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@ModelAttribute @Validated CreateEditProductDTO create,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("products", create);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v4";
        }
        storageService.create(create);
        return "redirect:/v4";
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(@ModelAttribute @Validated CreateEditProductDTO update, @PathVariable Long id,
                         BindingResult bindingResult,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("products", update);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v4";
        }
        storageService.update(id, update);
        return "redirect:/v4";
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
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @SessionAttribute("userId") Long userId){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v4/"+id;
        }
        storageService.unReservation(orderDTO, id);
        return "redirect:/v4/"+id;
    }

    @ExceptionHandler
    public String exceptions(Model model, Exception e){
        model.addAttribute("errors", e.getMessage());
        return "error";
    }
}

