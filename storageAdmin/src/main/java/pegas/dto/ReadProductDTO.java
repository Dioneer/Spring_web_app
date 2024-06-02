package pegas.dto;

import lombok.Value;

@Value
public class ReadProductDTO {
    Long id;
    String productMark;
    String productModel;
    String price;
    Integer amount;
    Integer reserved;
    String productImage;
}
