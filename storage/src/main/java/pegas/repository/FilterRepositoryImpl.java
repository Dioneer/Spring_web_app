package pegas.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import pegas.dto.ProductFilter;
import pegas.dto.QPredicates;
import pegas.entity.Product;

import java.util.List;

import static pegas.entity.QProduct.product;

@RequiredArgsConstructor
public class FilterRepositoryImpl implements FilterRepository{
    private final EntityManager entityManager;

    @Override
    public List<Product> filter(ProductFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.productMark(), product.productMark::containsIgnoreCase)
                .add(filter.productModel(), product.productModel::containsIgnoreCase)
                .add(filter.price(), product.price::eq)
                .build();
        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();
    }
}
