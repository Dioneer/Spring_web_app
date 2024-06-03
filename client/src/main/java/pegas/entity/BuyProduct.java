package pegas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="buy_products")
public class BuyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "product_id")
    private Long productId;
    @Column(nullable = false, name = "user_id")
    private Long userId;
    @Column(nullable = false,  name = "product_mark")
    private String productMark;
    @Column(nullable = false, name = "product_model")
    private String productModel;
    @Column(nullable = false)
    private BigDecimal price;
    private Integer amount;
}
