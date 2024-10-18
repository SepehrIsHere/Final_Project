package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.ReceiptDto;
import ir.maktabsharif.finalproject.entities.Receipt;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptService {

    void add(Receipt receipt);

    void update(Receipt receipt);

    void delete(Receipt receipt);

    List<ReceiptDto> findAll();

    Receipt findById(int id);

    List<ReceiptDto> findByCustomer(String firstName, String lastName);

    List<ReceiptDto> findBySpecialist(String firstName, String lastName);

    ReceiptDto findByNameOfOrder(String nameOfOrder);
}
