package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    /**
     * CRUD find all
     * restController
     * @return
     */
    @PostMapping("/all")
    public ResponseEntity<List<Payment>> findAll(){
        return ResponseEntity.ok().body(paymentService.findAll());
    }

    /**
     * find paym,ent by user id
     * @param id of user
     * @return ResponseEntity<Payment>
     */
    @PostMapping("/{id}")
    public ResponseEntity<Payment> findByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(paymentService.findByUserId(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found")));
    }

    /**
     * find payment by cart number
     * @param userDataFind
     * @return ResponseEntity<Payment>
     */
    @PostMapping("/personalInfo")
    public ResponseEntity<Payment> findByCartNumber(@RequestBody @Validated UserDataFind userDataFind){
            return ResponseEntity.ok().body(paymentService.findByCartNumber(userDataFind)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found")));
    }

    /**
     * payment transaction
     * @param transfer
     * @return
     */
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
