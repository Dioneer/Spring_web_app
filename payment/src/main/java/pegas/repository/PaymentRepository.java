package pegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pegas.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByCartNumber(Long num);
    Payment findByUserId(Long id);
}
