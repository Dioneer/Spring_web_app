package pegas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "client_number")
    private Long cartNumber;
    @Column(name = "storage_balance")
    private BigDecimal storageBalance;
    @Column(name = "cart_balance")
    private BigDecimal cartBalance;
    @Column(name = "user_id")
    private Long userId;

}
