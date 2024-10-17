package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    @Query("SELECT r FROM Receipt r WHERE r.customerFirstName =:firstName AND r.customerLastName =:lastName")
    List<Receipt> findByCustomer(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT r FROM Receipt r WHERE r.specialistFirstName =:firstName AND r.specialistLastName =:lastName")
    List<Receipt> findBySpecialist(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT r FROM Receipt r WHERE r.nameOfOrder = :nameOfOrder")
    Receipt findByNameOfOrder(@Param("nameOfOrder") String nameOfOrder);
}
