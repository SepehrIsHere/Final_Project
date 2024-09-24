package service.impl;

import entities.Comment;
import repository.CommentRepository;
import service.CommentService;
import util.ValidationUtil;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ValidationUtil validationUtil;

    public CommentServiceImpl(CommentRepository commentRepository,ValidationUtil validationUtil) {
        this.commentRepository = commentRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void add(Comment comment) {
        try {
            validationUtil.validation(comment);
            commentRepository.add(comment);
        } catch (Exception e) {
            System.out.println("An error occured while adding a comment" + e.getMessage());
        }
    }

    @Override
    public void update(Comment comment) {
        try {
            validationUtil.validation(comment);
            commentRepository.update(comment);
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
    public List<Comment> findByCustomer(int customerId) {
        try {
            return commentRepository.findByCustomer(customerId);
        } catch (Exception e) {
            System.out.println("An error occured while finding customer's comments " + e.getMessage());
        }
        return null;
    }
}
