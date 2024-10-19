package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.entities.CreditCard;
import ir.maktabsharif.finalproject.service.PaymentService;
import ir.maktabsharif.finalproject.service.ReceiptService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final ValidationUtil validationUtil;
    private final PaymentService paymentService;
    private final ReceiptService receiptService;
    private final HttpSession session;
    private final MapperUtil mapperUtil;

    @PatchMapping("PATCH/payment/credit/{nameOfOrder}")
    ResponseEntity<Void> payWithCustomerCredit(@PathVariable String nameOfOrder){
        paymentService.payWithCustomerCredit(nameOfOrder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/processPayment")
    public String processPayment(@RequestParam String cardNumber, @RequestParam int cvv,
                                 @RequestParam int expMonth, @RequestParam int expYear, Model model) {

        if (!isValidCardNumber(cardNumber,cvv,expMonth,expYear)) {
            model.addAttribute("error", "Invalid card number.");
            return "payment";
        }
        return "payment-success";
    }

    private boolean isValidCardNumber(String cardNumber,int cvv,int expMonth,int expYear) {
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(cardNumber)
                .cvv2(cvv).expireMonth(expMonth)
                .expireYear(expYear)
                .build();
        return validationUtil.isValid(creditCard);
    }

}
