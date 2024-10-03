package ir.maktabsharif.finalproject.repository;

import com.github.javafaker.Faker;
import ir.maktabsharif.finalproject.util.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class SuggestionsRepositoryTest {

    private final SpecialistRepository specialistRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final Faker faker;

    @Autowired
    public SuggestionsRepositoryTest(SpecialistRepository specialistRepository, OrderRepository orderRepository, CustomerRepository customerRepository, Faker faker) {
        this.specialistRepository = specialistRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.faker = faker;
    }

    @Test
    void findsByOrder() {
    }
    @Test
    void doesNotFindByOrder(){

    }

    @Test
    void findsById() {

    }

    @Test
    void doesNotFindById() {

    }
}