package pegas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.UserDataFind;
import pegas.dto.Transfer;
import pegas.entity.Payment;
import pegas.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    /**
     * find payment by cart number
     * @param userDataFind cart number
     * @return Optional<Payment>
     */
    public Optional<Payment> findByCartNumber(UserDataFind userDataFind){
        return Optional.of(paymentRepository.findByCartNumber(userDataFind.getCartNumber()));
    }

    /**
     * find payment by id of user
     * @param id of user
     * @return Optional<Payment>
     */
    public Optional<Payment> findByUserId(Long id){
        return Optional.of(paymentRepository.findByUserId(id));
    }

    /**
     * CRUD find all payments. Don't use yet
     * @return List<Payment>
     */
    public List<Payment> findAll(){
        return Optional.of(paymentRepository.findAll())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"nothing was found"));
    }

    /**
     * transactional of payment
     * @param transfer cart number and sum
     * @return String
     */
    @Transactional
    public String transaction(Transfer transfer) {
       return  Optional.of(paymentRepository.findByCartNumber(transfer.getCartNumber())).map(i->{
            if((i.getCartBalance().compareTo(transfer.getSum())) < 0) {
                return "insufficient funds";
            }else{
                i.setCartBalance(i.getCartBalance().subtract(transfer.getSum()));
                i.setStorageBalance(i.getStorageBalance().add(transfer.getSum()));
                paymentRepository.save(i);
                return "the payment was completed successfully";
            }
        }).orElse("no cart with this number");
    }

}
