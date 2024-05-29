package pegas.controller.storageController;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.service.storageService.StorageApi;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/storage")
@SessionAttributes(value = {"userId"})
public class StorageController {
    private final StorageApi storageApi;
    private final static Long HOME = 1l;

    @GetMapping
    public String findAllProducts(Model model, @RequestParam("id") Long userId){
        model.addAttribute("products", storageApi.getAll());
        model.addAttribute("userId", userId);
        return "index";
    }

    @GetMapping("/filter")
    public String findAllProductsByFilter(Model model, ProductFilter productFilter){
        model.addAttribute("products", storageApi.getAllByFilter(productFilter));
        model.addAttribute("filter", productFilter);
        return "index";
    }

    @GetMapping("/{id}")
    public String findProductsById(Model model, @PathVariable("id") Long id){
        return Optional.of(storageApi.getById(id)).map(i->{
        model.addAttribute("products", storageApi.getById(id));
        return "product";
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/reservation")
    public String reservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @SessionAttribute("userId") Long userId){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v3/storage?id="+userId;
        }
        storageApi.reservation(orderDTO, id);
        return "redirect:/v3/storage?id="+userId;
    }

    @PostMapping("/{id}/unreservation")
    public String unReservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @SessionAttribute("userId") Long userId){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v3/storage?id="+userId;
        }
        storageApi.unReservation(orderDTO, id);
        return "redirect:/v3/storage?id="+userId;
    }
    @PostMapping("/{id}/sale")
    public String productSale(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @SessionAttribute("userId") Long userId){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/v3/storage?id="+userId;
        }
        storageApi.sale(orderDTO, id);
        return "redirect:/v3/storage?id="+userId;
    }

}
