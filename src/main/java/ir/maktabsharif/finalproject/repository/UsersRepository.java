package ir.maktabsharif.finalproject.repository;


import ir.maktabsharif.finalproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Users findUserById(@Param("id") int id);

    @Query("SELECT u FROM Users u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    Users findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT u FROM Users u WHERE u.username = :username AND u.password = :password")
    Users loginUsers(String username, String password);
}
