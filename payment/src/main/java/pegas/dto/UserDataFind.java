package pegas.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import pegas.dto.validation.CartValidation;

@Data
public class UserDataFind {
    @Positive
    private Long cartNumber;
}
