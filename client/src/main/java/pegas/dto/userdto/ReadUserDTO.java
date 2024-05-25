package pegas.dto.userdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import pegas.entity.Role;

import java.time.LocalDate;

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
}
