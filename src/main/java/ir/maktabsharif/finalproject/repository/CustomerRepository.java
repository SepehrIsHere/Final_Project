package ir.maktabsharif.finalproject.repository;


import ir.maktabsharif.finalproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName")
    Customer findCustomerByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Customer findCustomerById(@Param("id") Integer id);

    @Query("SELECT c FROM Customer c ORDER BY c.firstName ASC")
    List<Customer> displayByFirstNameASC();

    @Query("SELECT c FROM Customer c ORDER BY c.lastName ASC")
    List<Customer> displayByLastNameASC();

    @Query("SELECT c FROM Customer c ORDER BY c.credit ASC")
    List<Customer> displayByCreditASC();

    @Query("SELECT c FROM Customer c ORDER BY c.credit DESC")
    List<Customer> displayByCreditDESC();
}