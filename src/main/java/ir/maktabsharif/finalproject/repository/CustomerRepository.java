package ir.maktabsharif.finalproject.repository;


import ir.maktabsharif.finalproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.user.firstName = :firstName AND c.user.lastName = :lastName")
    Customer findCustomerByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Customer findCustomerById(@Param("id") Integer id);
}
