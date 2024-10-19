package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Comment;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.customer = :customer")
    List<Comment> findByCustomer(Customer customer);

    @Query("SELECT c FROM Comment c WHERE c.specialist = :specialist")
    List<Comment> findBySpecialist(@Param("specialist") Specialist specialist);

    @Query("SELECT c FROM Comment c WHERE c.id = :id")
    Comment findById(int id);
}
