package pegas.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pegas.entity.Role;

import java.time.LocalDate;

@Data
@Builder
public class CreateUpdateUserDTO {
    @Email
    private String username;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdayDate;
    @NotBlank
    @Size(min=4, max=50)
    private String firstname;
    private String lastname;
    private Role role;
    private MultipartFile multipartFile;
}
