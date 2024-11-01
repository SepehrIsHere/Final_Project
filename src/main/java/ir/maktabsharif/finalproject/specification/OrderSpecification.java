package ir.maktabsharif.finalproject.specification;


import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@AllArgsConstructor
public class OrderSpecification implements Specification<Order> {

    private final Customer customer;
    private final Specialist specialist;
    private final SubTask subTask;
    private final Task task;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OrderStatus orderStatus;
    private final String orderByField;
    private final Boolean ascending;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        filterByCustomer(predicates,root,criteriaBuilder);
        filterBySpecialist(predicates,root,criteriaBuilder);
        filterBySubTask(predicates,root,criteriaBuilder);
        filterByTask(predicates,root,criteriaBuilder);
        filterByDateRange(predicates,root,criteriaBuilder);
        filterByStatus(predicates,root,criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void filterByCustomer(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (customer != null) {
            predicates.add(criteriaBuilder.equal(root.get("customer"), customer));
        }
    }

    private void filterBySpecialist(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (specialist != null) {
            predicates.add(criteriaBuilder.equal(root.get("specialist"), specialist));
        }
    }

    private void filterByDateRange(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (startDate != null && endDate != null) {
            predicates.add(criteriaBuilder.between(root.get("startDate"), startDate, endDate));
        }
    }

    private void filterByStatus(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (orderStatus != null) {
            predicates.add(criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
        }
    }

    private void filterBySubTask(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if(subTask != null){
            predicates.add(criteriaBuilder.equal(root.get("subTask"), subTask));
        }
    }

    private void filterByTask(List<Predicate> predicates, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if(task != null){
            predicates.add(criteriaBuilder.equal(root.get("task"), task));
        }
    }

    private void applySorting(CriteriaQuery<?> query, Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (orderByField != null && !orderByField.isEmpty()) {
            if (ascending) {
                query.orderBy(criteriaBuilder.asc(root.get(orderByField)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderByField)));
            }
        }
    }
}
