package pegas.dto.userdto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ReadReservedDTO {
    Long id;
    Long productId;
    String productMark;
    String productModel;
    BigDecimal price;
    Integer amount;
}
