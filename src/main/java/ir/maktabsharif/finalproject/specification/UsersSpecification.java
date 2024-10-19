package ir.maktabsharif.finalproject.specification;

import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UsersSpecification implements Specification<Users> {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final  String email;
    private final Role role;
    private String orderByField;
    private boolean ascending;

    @Override
    public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        filterByFirstName(predicates, root, criteriaBuilder);
        filterByLastName(predicates, root, criteriaBuilder);
        filterByUsername(predicates, root, criteriaBuilder);
        filterByEmail(predicates, root, criteriaBuilder);
        filterByRole(predicates, root, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void filterByFirstName(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
        }
    }

    private void filterByLastName(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
        }
    }

    private void filterByUsername(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (username != null && !username.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
        }
    }

    private void filterByEmail(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
        }
    }

    private void filterByPassword(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (password != null && !password.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("password"), "%" + password + "%"));
        }
    }

    private void filterByRole(List<Predicate> predicates, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (role != null) {
            predicates.add(criteriaBuilder.equal(root.get("role"), role));
        }
    }

    private void applySorting(CriteriaQuery<?> query, Root<Users> root, CriteriaBuilder criteriaBuilder) {
        if (orderByField != null) {
            if (ascending) {
                query.orderBy(criteriaBuilder.asc(root.get(orderByField)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderByField)));
            }
        }
    }

}
