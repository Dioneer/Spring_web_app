package pegas.dto.userdto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateReservedDTO {
    Long userId;
    Long productId;
    String productMark;
    String productModel;
    BigDecimal price;
    Integer amount;
}
