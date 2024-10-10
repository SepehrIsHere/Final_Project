package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.enumerations.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder().
                firstName("John").
                lastName("Doe").
                username("username").
                password("password123").
                email("example@gmail.com").
                role(Role.CUSTOMER).
                credit(0.0)
                .build();

        customerRepository.save(testCustomer);
    }

    @Test
    void testFindCustomerByFirstNameAndLastName() {
        Customer foundCustomer = customerRepository.findCustomerByFirstNameAndLastName("John", "Doe");

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getFirstName()).isEqualTo("John");
        assertThat(foundCustomer.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindCustomerById() {
        Customer foundCustomer = customerRepository.findCustomerById(testCustomer.getId());

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getId()).isEqualTo(testCustomer.getId());
    }

    @Test
    void testFindCustomerById_NotFound() {
        Customer foundCustomer = customerRepository.findCustomerById(999);

        assertThat(foundCustomer).isNull();
    }
}
