package pegas.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import pegas.dto.validation.CartValidation;

import java.math.BigDecimal;

@Data
@CartValidation
public class Transfer {
    @Positive
    private Long cartNumber;
    @Positive
    private BigDecimal sum;
}
