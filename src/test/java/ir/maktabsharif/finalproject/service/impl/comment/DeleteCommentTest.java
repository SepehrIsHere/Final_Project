package ir.maktabsharif.finalproject.service.impl.comment;

import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.repository.CommentRepository;
import ir.maktabsharif.finalproject.service.impl.CommentServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteCommentTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, validationUtil);
    }

    @AfterEach
    void tearDown(){
        commentRepository.deleteAll();
    }

    Comment generatedValidComment(){
        Specialist specialist = Specialist.
                builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpas123").
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .build();
        Customer customer = Customer.builder().firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpas123").
                role(Role.SPECIALIST).
                credit(BigDecimal.ZERO).
                build();
        return Comment.builder().
                text("test text for comment").
                ratingPoint(0.5).specialist(specialist).
                customer(customer).
                build();
    }

    Comment generateInvalidComment(){
        Specialist specialist = Specialist.
                builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpas123").
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .build();
        Customer customer = Customer.builder().firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpas123").
                role(Role.SPECIALIST).
                credit(BigDecimal.ZERO).
                build();
        return Comment.builder().
                customer(customer).
                specialist(specialist).
                ratingPoint(0.5).
                build();
    }
    @Test
    void delete_ShouldDeleteComment_WhenCalled() {
        // Arrange
        Comment comment = generatedValidComment();

        // Act
        commentService.delete(comment);

        // Assert
        verify(commentRepository).delete(comment);
    }

    @Test
    void delete_ShouldHandleException_WhenExceptionOccurs() {
        // Arrange
        Comment comment = generateInvalidComment();
        doThrow(new RuntimeException("Delete failed")).when(commentRepository).delete(comment);

        // Act
        commentService.delete(comment);

        // Assert
        verify(commentRepository).delete(comment);
    }

}
