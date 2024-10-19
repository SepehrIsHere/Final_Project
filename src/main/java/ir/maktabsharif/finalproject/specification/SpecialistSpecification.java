package ir.maktabsharif.finalproject.specification;

import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SpecialistSpecification implements Specification<Specialist> {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private double score;
    private final String subTaskName;
    private String orderByField;
    private boolean ascending;

    @Override
    public Predicate toPredicate(Root<Specialist> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        filterByFirstName(predicates, root, criteriaBuilder);
        filterByLastName(predicates, root, criteriaBuilder);
        filterByUsername(predicates, root, criteriaBuilder);
        filterByEmail(predicates, root, criteriaBuilder);
        filterByScore(predicates, root, criteriaBuilder);
        filterBySubTask(predicates, root, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void filterBySubTask(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (subTaskName != null && !subTaskName.isEmpty()) {
            Join<Specialist, SubTask> subTaskJoin = root.join("subTasks", JoinType.INNER);
            predicates.add(criteriaBuilder.like(subTaskJoin.get("name"), "%" + subTaskName + "%"));
        }
    }

    private void filterByFirstName(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), firstName));
        }
    }

    private void filterByLastName(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), lastName));
        }
    }

    private void filterByUsername(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (username != null && !username.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("username"), username));
        }
    }

    private void filterByEmail(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("email"), email));
        }
    }

    private void filterByScore(List<Predicate> predicates, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (!(score < 0)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("score"), score));
        }
    }

    private void applySorting(CriteriaQuery<?> query, Root<Specialist> root, CriteriaBuilder criteriaBuilder) {
        if (orderByField != null && !orderByField.isEmpty()) {
            if (ascending) {
                query.orderBy(criteriaBuilder.asc(root.get(orderByField)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderByField)));
            }
        }
    }
}
