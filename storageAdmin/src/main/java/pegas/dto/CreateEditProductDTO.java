package pegas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class CreateEditProductDTO implements Serializable {
    @NotBlank
    @Size(min=3)
    String productMark;
    @NotBlank
    @Size(min=3)
    String productModel;
    @Positive
    String price;
    @Positive
    Integer amount;
    @NotNull
    Integer reserved;
    MultipartFile productImage;
}
