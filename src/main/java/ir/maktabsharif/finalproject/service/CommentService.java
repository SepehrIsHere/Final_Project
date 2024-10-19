package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentService {
    void add(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

    List<Comment> findAll();

    Comment findById(int id);

    List<Comment> findBySpecialist(String specialistFirstName,String specialistLastName);

    Comment addAComment(CommentDto commentDto);
}
