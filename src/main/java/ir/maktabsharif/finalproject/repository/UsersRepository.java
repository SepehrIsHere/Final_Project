package ir.maktabsharif.finalproject.repository;


import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> , JpaSpecificationExecutor<Users> {
    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Users findUserById(@Param("id") int id);

    @Query("SELECT u FROM Users u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    Users findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT u FROM Users u WHERE u.username = :username AND u.password = :password")
    Users loginUsers(String username, String password);

    @Query("SELECT u FROM Users u WHERE u.role = :role")
    List<Users> findByRole(@Param("role") Role role);

    @Query("SELECT u FROM Users u ORDER BY u.email ASC")
    List<Users> findAllOrderByEmailAsc();

    @Query("SELECT u FROM Users u ORDER BY u.email DESC")
    List<Users> findAllOrderByEmailDesc();

    @Query("SELECT u FROM Users u ORDER BY u.firstName ASC")
    List<Users> findAllOrderByFirstNameAsc();

    @Query("SELECT u FROM Users u ORDER BY u.lastName ASC")
    List<Users> findAllOrderByLastNameAsc();
}
