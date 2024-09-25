package repository;

import entities.Order;
import enumerations.OrderStatus;

import java.util.List;

public interface OrderRepository extends BaseEntityRepository<Order> {
    Order findById(int orderId);

    List<Order> findAll();

    List<Order> findByOrderStatusAndCustomer(OrderStatus orderStatus, int customerId);


}
