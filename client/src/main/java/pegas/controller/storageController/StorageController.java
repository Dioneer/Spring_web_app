package pegas.controller.storageController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pegas.entity.storage.OrderDTO;
import pegas.entity.storage.ProductFilter;
import pegas.service.storageService.StorageApi;

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
    public String findAllProductsByFilter(Model model,@RequestBody ProductFilter productFilter){
        model.addAttribute("products", storageApi.getAllByFilter(productFilter));
        return "index";
    }

    @GetMapping("/{id}")
    public String findProductsById(Model model, @PathVariable("id") Long id){
        model.addAttribute("products", storageApi.getById(id));
        return "index";
    }

    @PostMapping("/{id}/reservation")
    public String reservation(Model model, @PathVariable("id") Long id, @ModelAttribute OrderDTO orderDTO){
        model.addAttribute("products", storageApi.reservation(orderDTO, id));
        return "index";
    }

    @PostMapping("/{id}/unreservation")
    public String unReservation(Model model, @PathVariable("id") Long id, @ModelAttribute OrderDTO orderDTO){
        model.addAttribute("products", storageApi.unReservation(orderDTO, id));
        return "index";
    }
    @PostMapping("/{id}/sale")
    public String productSale(Model model, @PathVariable("id") Long id, @ModelAttribute OrderDTO orderDTO){
        model.addAttribute("products", storageApi.sale(orderDTO, id));
        return "index";
    }

}
