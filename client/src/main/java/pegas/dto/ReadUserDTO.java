package pegas.dto;

import lombok.Value;
import pegas.entity.Role;

import java.time.LocalDate;

@Value
public class ReadUserDTO {
    Long id;
    String username;
    LocalDate birthdayDate;
    String firstname;
    String lastname;
    Role role;
}
