package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CreditCardDto;
import ir.maktabsharif.finalproject.dto.ReceiptDto;

public interface PaymentService {
    void payWithCustomerCredit(String nameOfOrder);

    boolean processPayment(CreditCardDto creditCardDto,ReceiptDto receiptDto);
}
