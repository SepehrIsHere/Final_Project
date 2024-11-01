package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.CreditCardDto;
import ir.maktabsharif.finalproject.entities.CreditCard;
import ir.maktabsharif.finalproject.service.PaymentService;
import ir.maktabsharif.finalproject.service.ReceiptService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final Random random = new Random();
    private final PaymentService paymentService;

    @GetMapping("/captcha")
    public ResponseEntity<byte[]> generateCaptcha(HttpServletRequest request) throws IOException {
        String captchaText = String.format("%04d", random.nextInt(10000));

        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);

        BufferedImage captchaImage = new BufferedImage(100, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = captchaImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 40);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        graphics.drawString(captchaText, 10, 30);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/pay-online")
    public ResponseEntity<String> processPayment(@RequestBody CreditCardDto creditCardDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (!creditCardDto.getCaptcha().equals(sessionCaptcha)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid captcha. Please try again.");
        }
        session.removeAttribute("captcha");
        return ResponseEntity.ok("Payment processed successfully!");
    }

    @GetMapping("/pay-online")
    public String showPaymentForm() {
        return "payment-view";
    }

    @GetMapping("/payment-success")
    public String showPaymentSuccess() {
        return "payment-success";
    }

    @PatchMapping("customer/payment/credit/{nameOfOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    ResponseEntity<Void> payWithCustomerCredit(@PathVariable String nameOfOrder){
        paymentService.payWithCustomerCredit(nameOfOrder);
        return ResponseEntity.ok().build();
    }

}
