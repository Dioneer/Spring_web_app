package pegas.entity.payment;

import jakarta.persistence.Column;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentDTO {
    Long id;
    Long cartNumber;
    BigDecimal storageBalance;
    BigDecimal cartBalance;
}
