package pegas.controller.storageController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.payment.TransferDTO;
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.service.clientService.ClientService;
import pegas.service.paymentService.PaymentApi;
import pegas.service.storageService.StorageApi;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/storage")
@SessionAttributes(value = {"userId"})
public class StorageController {
    private final StorageApi storageApi;
    private final ClientService clientService;
    private final PaymentApi paymentApi;

    @GetMapping
    public String findAllProducts(Model model,  @RequestParam(name = "id", required = false) Long userId){
        model.addAttribute("products", storageApi.getAll());
        model.addAttribute("userId", userId);
        return "index";
    }

    @PostMapping("/home")
    public String home(@SessionAttribute("userId") Long userId){
        return "redirect:/v3/users/"+userId;
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
        clientService.createRes(id, orderDTO.getAmount(), userId);
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
        clientService.unreserved(id, orderDTO.getAmount(), userId);
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
        String price = storageApi.getById(id).getPrice();
        Long number= paymentApi.findById(userId).getCartNumber();
        BigDecimal sum = new BigDecimal(orderDTO.getAmount()).multiply(new BigDecimal(price));
        TransferDTO transferDTO = new TransferDTO(number,sum);
        String result = paymentApi.pay(transferDTO);
        if(result==null) {
            storageApi.sale(orderDTO, id);
            clientService.createBuy(id, orderDTO.getAmount(), userId);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, result);
        }
        return "redirect:/v3/storage?id="+userId;
    }

}
