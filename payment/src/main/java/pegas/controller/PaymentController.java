package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.UserDataFind;
import pegas.dto.Transfer;
import pegas.entity.Payment;
import pegas.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/all")
    public ResponseEntity<List<Payment>> findAll(){
        return ResponseEntity.ok().body(paymentService.findAll());
    }

    @PostMapping("/personalInfo")
    public ResponseEntity<Payment> findByCartNumber(@RequestBody @Validated UserDataFind userDataFind){
            return ResponseEntity.ok().body(paymentService.findByCartNumber(userDataFind)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found")));
    }

    @PostMapping
    public ResponseEntity<String> payment(@RequestBody @Validated Transfer transfer){
        try{
            String answer = paymentService.transaction(transfer);
            if(answer.equals("the payment was completed successfully")){
                return ResponseEntity.ok().body(answer);
            }else{
                return ResponseEntity.badRequest().body(answer);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("some problems with fields");
        }
    }
}
