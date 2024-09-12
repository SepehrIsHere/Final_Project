package service.impl;

import entities.Comment;
import repository.CommentRepository;
import service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void add(Comment comment) {
        try {
            commentRepository.add(comment);
        } catch (Exception e) {
            System.out.println("An error occured while adding a comment" + e.getMessage());
        }
    }

    @Override
    public void update(Comment comment) {
        try {
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
}
