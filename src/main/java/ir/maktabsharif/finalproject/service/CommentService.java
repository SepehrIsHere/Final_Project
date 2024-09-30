package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;

import java.util.List;

public interface CommentService {
    void add(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

    List<Comment> findAll();

    Comment findById(int id);

    List<Comment> findByCustomer(Customer customer);
}
