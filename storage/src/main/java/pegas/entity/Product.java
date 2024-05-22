package pegas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "product_mark")
    private String productMark;
    @Column(nullable = false, name = "product_model")
    private String productModel;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer amount;
    private Integer reserved;
    @Column(name = "product_image")
    private String productImage;
}
