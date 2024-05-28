package pegas.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pegas.entity.Role;

import java.time.LocalDate;

@Value
public class CreateUpdateUserDTO {
    @Email
    String username;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthdayDate;
    @NotBlank
    @Size(min=4, max=50)
    String firstname;
    String lastname;
    Role role;
    MultipartFile multipartFile;
}
