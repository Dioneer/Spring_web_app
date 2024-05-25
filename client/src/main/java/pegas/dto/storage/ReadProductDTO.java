package pegas.dto.storage;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReadProductDTO {
    Long id;
    String productMark;
    String productModel;
    @Positive
    String price;
    @Positive
    Integer amount;
    @Positive
    Integer reserved;
    String productImage;
}
