package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.repository.CommentRepository;
import ir.maktabsharif.finalproject.service.CommentService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,ValidationUtil validationUtil) {
        this.commentRepository = commentRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void add(Comment comment) {
        try {
            if (validationUtil.isValid(comment)) {
                commentRepository.save(comment);
            } else {
                validationUtil.displayViolations(comment);
            }
        } catch (Exception e) {
            System.out.println("An error occured while adding a comment" + e.getMessage());
        }
    }

    @Override
    public void update(Comment comment) {
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            System.out.println("An error occured while updating a comment" + e.getMessage());
        }
    }

    @Override
    public void delete(Comment comment) {
        try {
            commentRepository.delete(comment);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a comment" + e.getMessage());
        }
    }

    @Override
    public List<Comment> findAll() {
        try {
            return commentRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all comments" + e.getMessage());
        }
        return null;
    }

    @Override
    public Comment findById(int id) {
        try {
            return commentRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding a comment" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Comment> findByCustomer(Customer customer) {
        try {
            return commentRepository.findByCustomer(customer);
        } catch (Exception e) {
            System.out.println("An error occured while finding customer's comments " + e.getMessage());
        }
        return null;
    }
}
