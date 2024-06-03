package pegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pegas.entity.BuyProduct;
import pegas.entity.ReserveProduct;

import java.util.List;
import java.util.Optional;

public interface BuyProductRepository extends JpaRepository<BuyProduct, Long> {
    List<BuyProduct> findByUserId(Long userId);
    @Query("select b from BuyProduct b where b.productId = :productId and b.userId = :userId")
    Optional<BuyProduct> findByUserIdAndProductId(Long userId, Long productId);
}
