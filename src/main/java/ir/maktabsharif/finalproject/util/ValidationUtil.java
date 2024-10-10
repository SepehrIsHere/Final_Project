package ir.maktabsharif.finalproject.util;

import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtil {
    private final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public void displayViolations(Object object) throws InvalidFieldValueException {
      throw new InvalidFieldValueException("Objects some fields are not valid ! ");
    }

    public boolean isValid(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        return violations.isEmpty();
    }
}
