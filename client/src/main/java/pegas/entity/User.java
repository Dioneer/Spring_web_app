package pegas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@ToString(exclude = {"buyProduct","reserveProduct"})
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;
    @Column(nullable = false)
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private String password;
    @Column(name = "user_image")
    private String image;
    @Builder.Default
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BuyProduct> buyProduct = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "userId",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReserveProduct> reserveProduct = new ArrayList<>();
}
