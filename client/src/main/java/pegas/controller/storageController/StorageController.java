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
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.ReserveProduct;
import pegas.service.clientService.ClientService;
import pegas.service.integration.FileGateWay;
import pegas.service.paymentService.PaymentApi;
import pegas.service.storageService.StorageApi;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/storage")
@SessionAttributes(value = {"userId"})
public class StorageController {
    private final StorageApi storageApi;
    private final ClientService clientService;
    private final PaymentApi paymentApi;
    private final FileGateWay fileGateWay;


    /**
     * Show products after login. Add count for cart
     * @param model standard object of java
     * @param userId query parameter for identification of user
     * @return html view index.html
     */
    @GetMapping
    public String findAllProducts(Model model,  @RequestParam(name = "id", required = false) Long userId){
        model.addAttribute("products", storageApi.getAll());
        model.addAttribute("userId", userId);
        List<ReserveProduct> list = clientService.findById(userId).map(ReadUserDTO::getReserve).orElse(null);
        assert list != null;
        model.addAttribute("count", list.stream().map(ReserveProduct::getAmount).reduce(0, Integer::sum));
        return "index";
    }

    /**
     * controller for comeback to personal account
     * @param userId query parameter for identification of user
     * @return redirect to personal account
     */
    @PostMapping("/home")
    public String home(@SessionAttribute("userId") Long userId){
        return "redirect:/v3/users/"+userId;
    }

    /**
     * product filtrated by Model Mark and Price parameters. Add count for cart
     * @param model standard object of java
     * @param productFilter object to filter parameters
     * @param userId query parameter for identification of user
     * @return html view index.html
     */
    @GetMapping("/filter")
    public String findAllProductsByFilter(Model model, ProductFilter productFilter,@SessionAttribute("userId") Long userId){
        model.addAttribute("products", storageApi.getAllByFilter(productFilter));
        model.addAttribute("filter", productFilter);
        List<ReserveProduct> list = clientService.findById(userId).map(ReadUserDTO::getReserve).orElse(null);
        assert list != null;
        model.addAttribute("count", list.stream().map(ReserveProduct::getAmount).reduce(0, Integer::sum));
        return "index";
    }

    /**
     * find just 1 product. Add count for cart
     * @param model standard object of java
     * @param id of product
     * @param userId query parameter for identification of user.
     * @return html view product.html
     */
    @GetMapping("/{id}")
    public String findProductsById(Model model, @PathVariable("id") Long id, @SessionAttribute("userId") Long userId){
        return Optional.of(storageApi.getById(id)).map(i->{
        model.addAttribute("products", storageApi.getById(id));
            List<ReserveProduct> list = clientService.findById(userId).map(ReadUserDTO::getReserve).orElse(null);
            assert list != null;
            model.addAttribute("count", list.stream().map(ReserveProduct::getAmount).reduce(0, Integer::sum));
        return "product";
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * method for reservation
     * @param model standard object of java
     * @param id of user
     * @param orderDTO object for rest service
     * @param bindingResult collect all errors from @validated
     * @param redirectAttributes redirect attributes include object parameters
     * @param userId query parameter for identification of user
     * @return redirect to page with all products
     */
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
        fileGateWay.writeToFile("reservation.txt",userId.toString()+" reserved "+id.toString() +" amount: "
                +orderDTO.toString());
        return "redirect:/v3/storage?id="+userId;
    }

    /**
     * method for unreservation
     * @param model standard object of java
     * @param id of user
     * @param orderDTO object for rest service
     * @param bindingResult collect all errors from @validated
     * @param redirectAttributes redirect attributes include object parameters
     * @param userId query parameter for identification of user
     * @return redirect to page with all products
     */
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
        fileGateWay.writeToFile("unreservation.txt",userId.toString()+" unreserved "+id.toString() +" amount: "
                +orderDTO.toString());
        return "redirect:/v3/storage?id="+userId;
    }

    /**
     * method for sale
     * @param model standard object of java
     * @param id of user
     * @param orderDTO object for rest service
     * @param bindingResult collect all errors from @validated
     * @param redirectAttributes redirect attributes include object parameters
     * @param userId query parameter for identification of user
     * @return redirect to page with all products
     */
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
            fileGateWay.writeToFile("sale.txt",userId.toString()+" bought "+id.toString() +" amount: "
                    +orderDTO.toString());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, result);
        }
        return "redirect:/v3/storage?id="+userId;
    }

}
