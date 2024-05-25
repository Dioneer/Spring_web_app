package pegas.dto.storage;

import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class OrderDTO {
    @Positive
    int amount;
}
