package pegas.dto;

import lombok.Value;

@Value
public class CreateEditProductDTO {
    String productMark;
    String productModel;
    String price;
    Integer amount;
    Integer reserved;
    String productImage;
}
