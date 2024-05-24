package pegas.entity.storage;

import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class OrderDTO {
    @Positive
    int amount;
}
