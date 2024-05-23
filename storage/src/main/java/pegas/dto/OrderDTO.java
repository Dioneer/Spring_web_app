package pegas.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDTO {
    @Positive
    int amount;
}
