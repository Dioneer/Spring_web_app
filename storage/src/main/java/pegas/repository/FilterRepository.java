package pegas.repository;

import pegas.dto.ProductFilter;
import pegas.entity.Product;

import java.util.List;

public interface FilterRepository {
    List<Product> filter(ProductFilter filter);
}
