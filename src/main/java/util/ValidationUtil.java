package util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class ValidationUtil {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public void validation(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            System.out.println(object.getClass().getName() + " is valid!");
        }
    }

    public void displayViolations(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }

    public boolean isValid(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        return violations.isEmpty();
    }
}
