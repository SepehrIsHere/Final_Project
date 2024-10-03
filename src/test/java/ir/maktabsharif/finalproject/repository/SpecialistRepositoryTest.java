package ir.maktabsharif.finalproject.repository;

import com.github.javafaker.Faker;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.util.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class SpecialistRepositoryTest {
    private final SpecialistRepository specialistRepository;
    private final Faker faker;

    @Autowired
    public SpecialistRepositoryTest(SpecialistRepository specialistRepository, Faker faker) {
        this.specialistRepository = specialistRepository;
        this.faker = faker;
    }

    String generateValidPassword() {
        return faker.regexify("[a-zA-Z]{5}[0-9]{3}");
    }

    @Test
    void findsSpecialistByStatus() {
        Specialist specialist = Specialist.builder().
                score(0.0).
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL).build();

        Specialist specialist1 = Specialist.builder().
                score(0.0).
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL).build();

        Specialist specialist2 = Specialist.builder().
                score(0.0).
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL).build();
        specialistRepository.save(specialist);
        specialistRepository.save(specialist1);
        specialistRepository.save(specialist2);

        List<Specialist> expectedSpecialist = specialistRepository.findSpecialistByStatus(SpecialistStatus.PENDING_APPROVAL);
        assertEquals(3, expectedSpecialist.size());
        assertNotNull(expectedSpecialist);
        assertEquals(expectedSpecialist.get(0), specialist);
        assertEquals(expectedSpecialist.get(1), specialist1);
        assertEquals(expectedSpecialist.get(2), specialist2);
    }

    @Test
    void doesNotFindsBySpecialistStatus() {
        List<Specialist> approvedSpecialist = specialistRepository.findSpecialistByStatus(SpecialistStatus.APPROVED);

        assertNotNull(approvedSpecialist);
        assertEquals(0, approvedSpecialist.size());
    }

    @Test
    void findsSpecialistById() {
        Specialist specialist = Specialist.builder().
                score(0.0).
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL).build();
        specialistRepository.save(specialist);

        Specialist expectedSpecialist = specialistRepository.findSpecialistById(specialist.getId());

        assertNotNull(expectedSpecialist);
        assertEquals(expectedSpecialist.getScore(), specialist.getScore());
        assertEquals(expectedSpecialist.getId(), specialist.getId());
        assertEquals(expectedSpecialist.getFirstName(), specialist.getFirstName());
        assertEquals(expectedSpecialist.getLastName(), specialist.getLastName());
        assertEquals(expectedSpecialist.getEmail(), specialist.getEmail());
        assertEquals(expectedSpecialist.getUsername(), specialist.getUsername());
        assertEquals(expectedSpecialist.getPassword(), specialist.getPassword());
        assertEquals(expectedSpecialist.getRole(), specialist.getRole());
        assertEquals(expectedSpecialist.getSpecialistStatus(), specialist.getSpecialistStatus());
    }

    @Test
    void doesNotFindById() {
        int id = 123;
        Specialist specialist = specialistRepository.findSpecialistById(id);
        assertNull(specialist);
    }

    @Test
    void findsByFirstNameAndLastName() {
        Specialist specialist = Specialist.builder().
                score(0.0).
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL).build();
        specialistRepository.save(specialist);

        Specialist expectedSpecialist = specialistRepository.findByFirstNameAndLastName(specialist.getFirstName(), specialist.getLastName());

        assertNotNull(expectedSpecialist);
        assertEquals(expectedSpecialist.getScore(), specialist.getScore());
        assertEquals(expectedSpecialist.getId(), specialist.getId());
        assertEquals(expectedSpecialist.getFirstName(), specialist.getFirstName());
        assertEquals(expectedSpecialist.getLastName(), specialist.getLastName());
        assertEquals(expectedSpecialist.getEmail(), specialist.getEmail());
        assertEquals(expectedSpecialist.getUsername(), specialist.getUsername());
        assertEquals(expectedSpecialist.getPassword(), specialist.getPassword());
        assertEquals(expectedSpecialist.getRole(), specialist.getRole());
        assertEquals(expectedSpecialist.getSpecialistStatus(), specialist.getSpecialistStatus());
    }

    @Test
    void doesNotFindByFirstNameAndLastName() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        Specialist expectedSpecialist = specialistRepository.findByFirstNameAndLastName(firstName, lastName);
        assertNull(expectedSpecialist);
    }
}