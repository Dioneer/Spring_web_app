package pegas.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transfer {
    private Long cartNumber;
    private BigDecimal sum;
}
