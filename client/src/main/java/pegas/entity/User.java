package pegas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
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
    private String image;
}
