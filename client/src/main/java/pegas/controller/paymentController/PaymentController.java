package pegas.controller.paymentController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.payment.TransferDTO;
import pegas.dto.payment.UserCartDto;
import pegas.service.paymentService.PaymentApi;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v3/payment")
public class PaymentController {
    private final PaymentApi paymentApi;

    @GetMapping
    public String payment(Model model){
        model.addAttribute("payments", paymentApi.allPayments());
        return "payment";
    }

    @GetMapping("/{id}")
    public String findByUserId(Model model, @PathVariable Long id){
        model.addAttribute("payments", paymentApi.findById(id));
        return "payment";
    }


    @PostMapping("/cart")
    public String findByCartNumber(@RequestBody @Validated UserCartDto userCartDto,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("cart", userCartDto);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/payment";
        }
        paymentApi.cart(userCartDto);
        return "redirect:/v3/payment";
    }

    @PostMapping("/pay")
    public String pay(@RequestBody @Validated TransferDTO transferDTO,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("transfer", transferDTO);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v3/storage";
        }
        paymentApi.pay(transferDTO);
        return "redirect:/v3/storage";
    }
}
