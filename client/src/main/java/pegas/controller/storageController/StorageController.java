package pegas.controller.storageController;

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
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.service.storageService.StorageApi;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/storage")
public class StorageController {
    private final StorageApi storageApi;

    @GetMapping
    public String findAllProducts(Model model){
        model.addAttribute("products", storageApi.getAll());
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
        return "index";
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/reservation")
    public String reservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/storage";
        }
        storageApi.reservation(orderDTO, id);
        return "redirect:/v3/storage";
    }

    @PostMapping("/{id}/unreservation")
    public String unReservation(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/storage";
        }
        storageApi.unReservation(orderDTO, id);
        return "redirect:/v3/storage";
    }
    @PostMapping("/{id}/sale")
    public String productSale(Model model, @PathVariable("id") Long id, @ModelAttribute @Validated OrderDTO orderDTO,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/storage";
        }
        storageApi.sale(orderDTO, id);
        return "redirect:/v3/storage";
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception exception, HttpServletRequest request){
        log.error(request.getContextPath() + "failed request", exception);
        return "error";
    }

}
