package pegas.dto.payment;

import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentDTO {
    Long id;
    Long cartNumber;
    @Positive
    BigDecimal storageBalance;
    @Positive
    BigDecimal cartBalance;
}
