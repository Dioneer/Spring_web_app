package pegas.dto;

import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class OrderDTO {
    @Positive
    int amount;
}
