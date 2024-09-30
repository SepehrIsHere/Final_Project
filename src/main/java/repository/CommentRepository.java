package repository;

import entities.Comment;

import java.util.List;

public interface CommentRepository extends BaseEntityRepository<Comment> {
    Comment findById(int id);

    List<Comment> findAll();

    List<Comment> findByCustomer(int customerId);
}
