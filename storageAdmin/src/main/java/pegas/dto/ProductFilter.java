package pegas.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductFilter {
    String productMark;
    String productModel;
    BigDecimal price;
}

