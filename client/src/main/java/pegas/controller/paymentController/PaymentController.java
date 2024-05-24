package pegas.controller.paymentController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pegas.entity.payment.TransferDTO;
import pegas.entity.payment.UserCartDto;
import pegas.service.paymentService.PaymentApi;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/payment")
public class PaymentController {
    private final PaymentApi paymentApi;

    @GetMapping
    public String payment(Model model){
        model.addAttribute("products", paymentApi.allPayments());
        return "index";
    }

    @PostMapping("/cart")
    public String findByCartNumber(Model model, @RequestBody UserCartDto userCartDto){
        model.addAttribute("products", paymentApi.cart(userCartDto));
        return "index";
    }

    @PostMapping("/pay")
    public String pay(Model model, @RequestBody TransferDTO transferDTO){
        model.addAttribute("products", paymentApi.pay(transferDTO));
        return "index";
    }
}
