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
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentServiceImplTest {

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

    @Test
    void findByCustomer_ShouldReturnListOfComments_WhenSuccessful() {
        // Arrange
        Customer customer = new Customer();
        List<Comment> comments = List.of(generatedValidComment(),generatedValidComment());
        when(commentRepository.findByCustomer(customer)).thenReturn(comments);

        // Act
        List<Comment> result = commentService.findByCustomer(customer);

        // Assert
        verify(commentRepository).findByCustomer(customer);
        assertEquals(2, result.size());
    }

    @Test
    void findByCustomer_ShouldHandleException_WhenExceptionOccurs() {
        // Arrange
        Customer customer = new Customer();
        doThrow(new RuntimeException("Find by customer failed")).when(commentRepository).findByCustomer(customer);

        // Act
        List<Comment> result = commentService.findByCustomer(customer);

        // Assert
        verify(commentRepository).findByCustomer(customer);
        assertNull(result);
    }
}
