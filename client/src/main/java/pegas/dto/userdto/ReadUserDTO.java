package pegas.dto.userdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import pegas.entity.BuyProduct;
import pegas.entity.ReserveProduct;
import pegas.entity.Role;

import java.time.LocalDate;
import java.util.List;

@Value
public class ReadUserDTO {
    Long id;
    @NotBlank
    @Size(min=4, max=50)
    String username;
    LocalDate birthdayDate;
    @NotBlank
    String firstname;
    String lastname;
    Role role;
    String image;
    List<ReserveProduct> reserve;
    List<BuyProduct> buy;
}
