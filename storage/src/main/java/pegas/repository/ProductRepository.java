package pegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pegas.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,FilterRepository,
        QuerydslPredicateExecutor<Product> {
}
