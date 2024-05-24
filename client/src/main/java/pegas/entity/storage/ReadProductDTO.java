package pegas.entity.storage;

import lombok.Data;

@Data
public class ReadProductDTO {
    Long id;
    String productMark;
    String productModel;
    String price;
    Integer amount;
    Integer reserved;
    String productImage;
}
