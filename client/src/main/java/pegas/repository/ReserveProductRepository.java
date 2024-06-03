package pegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pegas.entity.ReserveProduct;

import java.util.List;
import java.util.Optional;

public interface ReserveProductRepository extends JpaRepository<ReserveProduct, Long> {
    List<ReserveProduct> findByUserId(Long userId);
    @Query("select r from ReserveProduct r where r.productId = :productId and r.userId = :userId")
    Optional<ReserveProduct> findByUserIdAndProductId(Long userId, Long productId);
}
