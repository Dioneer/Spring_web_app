package pegas.dto.payment;

import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class UserCartDto {
        @Positive
        private Long cartNumber;
}
