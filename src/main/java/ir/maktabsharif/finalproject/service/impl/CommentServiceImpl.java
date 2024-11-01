package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.exception.CommentOperationException;
import ir.maktabsharif.finalproject.repository.CommentRepository;
import ir.maktabsharif.finalproject.service.CommentService;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final ValidationUtil validationUtil;

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
    public List<Comment> findBySpecialist(String specialistFirstName, String specialistLastName) {
        try {
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
            return commentRepository.findBySpecialist(specialist);
        } catch (Exception e) {
            throw new CommentOperationException("An error occured while trying to find specialist comments");
        }
    }

    @Override
    public Comment addAComment(CommentDto commentDto) {
        try {
            Specialist specialist = specialistService.findByFirstNameAndLastName(commentDto.getSpecialistFirstName(), commentDto.getSpecialistLastName());
            Customer customer = customerService.findByFirstNameAndLastName(commentDto.getCustomerFirstName(), commentDto.getCustomerLastName());
            Comment comment = Comment.builder()
                    .specialist(specialist)
                    .customer(customer)
                    .ratingPoint(commentDto.getRating())
                    .text(commentDto.getDescription())
                    .build();
            specialist.setScore(specialist.getScore() + commentDto.getRating());
            specialistService.update(specialist);
            add(comment);
            return comment;
        } catch (Exception e) {
            throw new CommentOperationException("An error occured while trying to add comment for specialist");
        }
    }
}
