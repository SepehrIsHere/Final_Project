package ir.maktabsharif.finalproject.repository;


import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    Order findByCustomer(@Param("customer") Customer customer);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :id")
    Order findByCustomerId(@Param("id") int id);

    @Query("SELECT o FROM Order o WHERE o.customer = :customer AND o.status = :status ")
    List<Order> findByCustomerAndOrderStatus(@Param("customer")Customer customer, @Param("status") OrderStatus status);

}
