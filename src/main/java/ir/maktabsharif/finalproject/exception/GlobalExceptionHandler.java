package ir.maktabsharif.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughCredit.class)
    public ResponseEntity<String> notEnoughCredit(NotEnoughCreditException ex) {
        return new ResponseEntity<>(" Not Enough Credit " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<String> notEnoughCreditException(NotEnoughCreditException ex) {
        return new ResponseEntity<>(" Not Enough Credit " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<String> paymentFailed(PaymentFailedException ex) {
        return new ResponseEntity<>(" Payment Failed " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentOperationException.class)
    public ResponseEntity<String> paymentOperationException(PaymentOperationException ex) {
        return new ResponseEntity<>(" Payment Operation " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReceiptOperationException.class)
    public ResponseEntity<String> receiptOperationException(ReceiptOperationException ex) {
        return new ResponseEntity<>(" Receipt Operation " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpecialistIsNotAssignedException.class)
    public ResponseEntity<String> specialistIsNotAssigned(SpecialistIsNotAssignedException ex) {
        return new ResponseEntity<>(" Specialist Not Assigned " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreditCardNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(CreditCardNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SpecialistNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(SpecialistNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(CustomerNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TasksNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(TasksNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubTaskNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(SubTaskNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(OrderNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonalImageNotFound.class)
    public ResponseEntity<String> resourceNotFound(PersonalImageNotFound ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuggestionNotFound.class)
    public ResponseEntity<String> resourceNotFound(SuggestionNotFound ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(CommentNotFoundException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreditCardOperationException.class)
    public ResponseEntity<String> handleGeneralException(CreditCardOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SpecialistOperationException.class)
    public ResponseEntity<String> handleGeneralException(SpecialistOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerOperationException.class)
    public ResponseEntity<String> handleGeneralException(CustomerOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskOperationException.class)
    public ResponseEntity<String> handleGeneralException(TaskOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubTaskOperationException.class)
    public ResponseEntity<String> handleGeneralException(SubTaskOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderOperationException.class)
    public ResponseEntity<String> handleGeneralException(OrderOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuggestionOperationException.class)
    public ResponseEntity<String> handleGeneralException(SuggestionOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentOperationException.class)
    public ResponseEntity<String> handleGeneralException(CommentOperationException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFieldValueException.class)
    public ResponseEntity<String> handleValidationException(InvalidFieldValueException ex) {
        return new ResponseEntity<>(" Resource Not Found " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidDateException ex) {
        return new ResponseEntity<>("Invalid date" + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundByFilterException.class)
    public ResponseEntity<String> handleNotFoundFilter(NotFoundByFilterException ex) {
        return new ResponseEntity<>("Not Found by filter " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageOperationException.class)
    public ResponseEntity<String> handleGeneralException(ImageOperationException ex) {
        return new ResponseEntity<>("Image Operation Exception" + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSpecialistStatus.class)
    public ResponseEntity<String> handleGeneralException(InvalidSpecialistStatus ex){
        return new ResponseEntity<>("Specialist status is not valid" + ex.getMessage(),HttpStatus.FORBIDDEN);
    }
}
