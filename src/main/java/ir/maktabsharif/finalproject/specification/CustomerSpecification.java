package ir.maktabsharif.finalproject.specification;

import ir.maktabsharif.finalproject.entities.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerSpecification implements Specification<Customer> {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final double credit;
    private String orderByField;
    private boolean ascending;


    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        filterByFirstName(predicates, root, criteriaBuilder);
        filterByLastName(predicates, root, criteriaBuilder);
        filterByEmail(predicates, root, criteriaBuilder);
        filterByUsername(predicates, root, criteriaBuilder);
        filterByCredit(predicates, root, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void filterByFirstName(List<Predicate> predicates, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
        }
    }

    private void filterByLastName(List<Predicate> predicates, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
        }
    }

    private void filterByEmail(List<Predicate> predicates, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
        }
    }

    private void filterByUsername(List<Predicate> predicates, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (username != null && !username.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
        }
    }

    private void filterByCredit(List<Predicate> predicates, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (!(credit < 0)) {
            predicates.add(criteriaBuilder.isNotNull(root.get("credit")));
        }
    }

    private void applySorting(CriteriaQuery<?> query, Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        if (orderByField != null && !orderByField.isEmpty()) {
            if (ascending) {
                query.orderBy(criteriaBuilder.asc(root.get(orderByField)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderByField)));
            }
        }
    }
}
