package pegas.dto.payment;

import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class TransferDTO {
    @Positive Long cartNumber;
    @Positive BigDecimal sum;
}
