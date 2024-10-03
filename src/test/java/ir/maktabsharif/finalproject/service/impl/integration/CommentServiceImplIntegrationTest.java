package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.repository.CommentRepository;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.impl.CommentServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CommentServiceImplIntegrationTest {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SpecialistRepository specialistRepository;

    private Comment validComment;
    private Customer validCustomer;
    private Specialist validSpecialist;

    @BeforeEach
    void setUp() {
        validCustomer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .username("johndoe")
                .password("john12345")
                .role(Role.CUSTOMER)
                .credit(BigDecimal.ZERO)
                .build();
        customerRepository.saveAndFlush(validCustomer);

        validSpecialist = Specialist.builder().
                firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .username("test username")
                .password("testpass1234")
                .role(Role.SPECIALIST)
                .score(0.0)
                .specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .build();
        specialistRepository.saveAndFlush(validSpecialist);

        validComment = Comment.builder()
                .text("Great service!")
                .ratingPoint(0.0)
                .customer(validCustomer)
                .specialist(validSpecialist)
                .build();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    void add_ShouldSaveComment_WhenValid() {
        // Act
        commentService.add(validComment);

        // Assert
        Comment savedComment = commentRepository.findById(validComment.getId()).orElseThrow(() -> new RuntimeException("Comment not found"));
        assertNotNull(savedComment);
        assertEquals("Great service!", savedComment.getText());
    }

    @Test
    void update_ShouldUpdateComment_WhenValid() {
        // Arrange
        commentService.add(validComment);
        validComment.setText("Updated comment");

        // Act
        commentService.update(validComment);

        // Assert
        Comment updatedComment = commentRepository.findById(validComment.getId()).orElseThrow(() -> new RuntimeException("Comment not found"));
        assertEquals("Updated comment", updatedComment.getText());
    }

    @Test
    void delete_ShouldRemoveComment_WhenValid() {
        // Arrange
        commentService.add(validComment);

        // Act
        commentService.delete(validComment);

        // Assert
        Optional<Comment> deletedComment = commentRepository.findById(validComment.getId());
        assertFalse(deletedComment.isPresent(), "Comment should be deleted, but it still exists.");
    }

    @Test
    void findAll_ShouldReturnAllComments() {
        // Arrange
        commentService.add(validComment);

        // Act
        List<Comment> comments = commentService.findAll();

        // Assert
        assertFalse(comments.isEmpty());
        assertTrue(comments.contains(validComment));
    }

    @Test
    void findById_ShouldReturnComment_WhenExists() {
        // Arrange
        commentService.add(validComment);

        // Act
        Comment foundComment = commentService.findById(validComment.getId());

        // Assert
        assertNotNull(foundComment);
        assertEquals("Great service!", foundComment.getText());
    }

    @Test
    void findByCustomer_ShouldReturnCommentsForCustomer_WhenExists() {
        // Arrange
        commentService.add(validComment);

        // Act
        List<Comment> customerComments = commentService.findByCustomer(validCustomer);

        // Assert
        assertNotNull(customerComments);
        assertTrue(customerComments.contains(validComment));
    }
}
