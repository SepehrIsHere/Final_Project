package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.ReceiptDto;
import ir.maktabsharif.finalproject.entities.Receipt;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.exception.ReceiptOperationException;
import ir.maktabsharif.finalproject.repository.ReceiptRepository;
import ir.maktabsharif.finalproject.service.ReceiptService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final MapperUtil mapperUtil;
    private final ValidationUtil validationUtil;

    @Override
    public void add(Receipt receipt) {
        try {
            if (validationUtil.isValid(receipt)) {
                receiptRepository.save(receipt);
            } else {
                throw new InvalidFieldValueException("Reciept is invalid");
            }
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to add Receipt");
        }
    }

    @Override
    public void update(Receipt receipt) {
        try {
            receiptRepository.save(receipt);
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to update Receipt");
        }
    }

    @Override
    public void delete(Receipt receipt) {
        try {
            receiptRepository.delete(receipt);
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to delete Receipt");
        }
    }

    @Override
    public List<ReceiptDto> findAll() {
        try {
            return receiptRepository.findAll().stream().map(mapperUtil::convertToDto).toList();
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to find all Receipt");
        }
    }

    @Override
    public Receipt findById(int id) {
        try {
            return receiptRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to find Receipt");
        }
    }

    @Override
    public List<ReceiptDto> findByCustomer(String firstName, String lastName) {
        try {
            return receiptRepository.findByCustomer(firstName, lastName).stream().map(mapperUtil::convertToDto).toList();
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to find Receipt");
        }
    }

    @Override
    public List<ReceiptDto> findBySpecialist(String firstName, String lastName) {
        try {
            return receiptRepository.findBySpecialist(firstName, lastName).stream().map(mapperUtil::convertToDto).toList();
        } catch (Exception e) {
            throw new ReceiptOperationException("There was an error trying to find Receipt");
        }
    }

    @Override
    public ReceiptDto findByNameOfOrder(String nameOfOrder) {
        try {
            Receipt receipt = receiptRepository.findByNameOfOrder(nameOfOrder);
            if(receipt != null){
                return mapperUtil.convertToDto(receipt);
            }else{
                throw new ReceiptOperationException("Recepeit Not Found ");
            }
        } catch (Exception e) {
            throw new ReceiptOperationException("An error occured while finding Reciept");
        }
    }
}
