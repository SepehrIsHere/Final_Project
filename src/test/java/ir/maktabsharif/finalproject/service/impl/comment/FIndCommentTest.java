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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FIndCommentTest {
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
    void tearDown() {
        commentRepository.deleteAll();
    }

    Comment generatedValidComment() {
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

    @Test
    void findAll_ShouldReturnListOfComments_WhenSuccessful() {
        // Arrange
        List<Comment> comments = List.of(generatedValidComment(), generatedValidComment());
        when(commentRepository.findAll()).thenReturn(comments);

        // Act
        List<Comment> result = commentService.findAll();

        // Assert
        verify(commentRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findAll_ShouldHandleException_WhenExceptionOccurs() {
        // Arrange
        doThrow(new RuntimeException("Find all failed")).when(commentRepository).findAll();

        // Act
        List<Comment> result = commentService.findAll();

        // Assert
        verify(commentRepository).findAll();
        assertNull(result);
    }

    @Test
    void findById_ShouldReturnComment_WhenFound() {
        // Arrange
        Comment comment = generatedValidComment();
        when(commentRepository.findById(1)).thenReturn(comment);

        // Act
        Comment result = commentService.findById(1);

        // Assert
        verify(commentRepository).findById(1);
        assertEquals(comment, result);
    }

    @Test
    void findById_ShouldHandleException_WhenExceptionOccurs() {
        // Arrange
        doThrow(new RuntimeException("Find by ID failed")).when(commentRepository).findById(1);

        // Act
        Comment result = commentService.findById(1);

        // Assert
        verify(commentRepository).findById(1);
        assertNull(result);
    }

}
