package pegas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pegas.dto.PaymentFind;
import pegas.dto.Transfer;
import pegas.entity.Payment;
import pegas.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Optional<Payment> findByCartNumber(PaymentFind paymentFind){
        return Optional.of(paymentRepository.findByCartNumber(paymentFind.getCartNumber()));
    }

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }

    @Transactional
    public boolean transaction(Transfer transfer) {
        Payment payment = paymentRepository.findByCartNumber(transfer.getCartNumber());
        if((payment.getBalance().compareTo(transfer.getSum())) < 0) {
            return false;
        }else{
            payment.setBalance(payment.getBalance().subtract(transfer.getSum()));
            paymentRepository.save(payment);
            return true;
        }
    }

}
