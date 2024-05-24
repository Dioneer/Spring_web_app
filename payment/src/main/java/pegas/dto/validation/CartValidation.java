package pegas.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CartValidationImpl.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface CartValidation {

    String message() default "{Not correct cart number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


