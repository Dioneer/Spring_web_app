package pegas.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserDataFind {
    @Positive
    private Long cartNumber;
}
