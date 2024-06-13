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

    /**
     * this method for the development of the system is not currently used
     * @param model
     * @return view of html page
     */
    @GetMapping
    public String payment(Model model){
        model.addAttribute("payments", paymentApi.allPayments());
        return "payment";
    }

    /**
     * find payment table of user
     * @param model standard object of java
     * @param id of user
     * @return view of html page
     */
    @GetMapping("/{id}")
    public String findByUserId(Model model, @PathVariable Long id){
        model.addAttribute("payments", paymentApi.findById(id));
        return "payment";
    }

    /**
     *
     * @param model standard object of java
     * @param userCartDto dto for rest transfer
     * @param bindingResult collects all errors of validated
     * @param redirectAttributes redirect with object parameters
     * @return view of html page
     */
    @PostMapping("/cart")
    public String findByCartNumber(Model model, @ModelAttribute @Validated UserCartDto userCartDto,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "payment";
        }
        model.addAttribute("payments", paymentApi.cart(userCartDto));
        return "payment";
    }

}
