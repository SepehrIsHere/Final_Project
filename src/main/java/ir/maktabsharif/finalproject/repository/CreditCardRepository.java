package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    @Query("SELECT c FROM CreditCard c WHERE c.cardNumber = :cardNumber AND c.cvv2 = :cvv2")
    CreditCard findByCardNumberAndCvv2(@Param("cardNumber") String cardNumber, @Param("cvv2") String cvv2);

    @Query("SELECT c FROM CreditCard c WHERE c.cardNumber = :cardNumber")
    CreditCard findByCardNumber(@Param("cardNumber") String cardNumber);
}
