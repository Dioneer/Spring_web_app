package pegas.dto;

import java.math.BigDecimal;

public record ProductFilter(String productMark, String productModel, BigDecimal price) {
}
