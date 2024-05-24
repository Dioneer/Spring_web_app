package pegas.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pegas.dto.Transfer;

import static org.springframework.util.StringUtils.hasText;

@Component
public class CartValidationImpl implements ConstraintValidator<CartValidation, Transfer> {

    @Override
    public boolean isValid(Transfer transfer, ConstraintValidatorContext context) {
        String str = Long.toString(transfer.getCartNumber());
        System.out.println(str);
        return hasText(str);
    }
}
