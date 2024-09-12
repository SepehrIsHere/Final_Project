package repository;

import entities.Order;

import java.util.List;

public interface OrderRepository extends BaseEntityRepository<Order> {
    Order findById(int orderId);

    List<Order> findAll();

    List<Order> findWaitingForSpecialistProposalsOrders();

    List<Order> findWaitingForSpecialistSelectionOrders();

    List<Order> findWaitingForSpecialistArrival();

    List<Order> findStartedOrders();

    List<Order> findCompletedOrders();

    List<Order> findPaidOrders();
}
