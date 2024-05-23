package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pegas.dto.PaymentFind;
import pegas.dto.Transfer;
import pegas.service.PaymentService;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/all")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.ok().body(paymentService.findAll());
        }catch (Exception e){
            return new ResponseEntity<>(new ResponsePaymentHandler
                    (HttpStatus.NOT_FOUND.value(), "no payments here"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/personal")
    public ResponseEntity<?> findByCartNumber(@RequestBody PaymentFind paymentFind){
        try{
            return ResponseEntity.ok().body(paymentService.findByCartNumber(paymentFind));
        }catch (Exception e){
            return new ResponseEntity<>(new ResponsePaymentHandler
                    (HttpStatus.NOT_FOUND.value(), "no payment with this number"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> payment(@RequestBody Transfer transfer){
        try{
            if(paymentService.transaction(transfer)){
                return ResponseEntity.ok().body("done");
            }else{
                return ResponseEntity.badRequest().body("refuse");
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ResponsePaymentHandler
                    (HttpStatus.BAD_REQUEST.value(), "some problems with fields"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
