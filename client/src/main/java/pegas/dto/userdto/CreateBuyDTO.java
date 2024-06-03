package pegas.dto.userdto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateBuyDTO {
    Long userId;
    Long productId;
    String productMark;
    String productModel;
    BigDecimal price;
    Integer amount;
}
