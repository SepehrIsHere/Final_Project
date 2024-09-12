package service;

import entities.Comment;

import java.util.List;

public interface CommentService {
    void add(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

    List<Comment> findAll();

    Comment findById(int id);
}
