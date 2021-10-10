package me.patriksobol.bank;

import me.patriksobol.bank.controller.model.PersonRequest;
import me.patriksobol.bank.domain.entity.PersonEntity;
import me.patriksobol.bank.repository.PersonRepository;
import me.patriksobol.bank.service.IBankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class JUnitTests {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private IBankService bankService;

    @Test
    void checkIfPersonHasID() {
        final PersonEntity entity = new PersonEntity();
        entity.setName("Patrik");
        entity.setLastName("Sobol");
        entity.setEmail("ju.patrik.sobol@mail.com");
        personRepository.save(entity);
        personRepository.flush();

        final Optional<PersonEntity> found = personRepository.getByEmail("ju.patrik.sobol@mail.com");
        assertTrue(found.isPresent());

        assertEquals(entity.getId(), found.get().getId());
    }

    @Test
    void checkAuditing() {
        final PersonEntity entity = new PersonEntity();
        entity.setName("Patrik");
        entity.setLastName("Sobol");
        entity.setEmail("ju.patrik.sobol@mail.com");
        personRepository.save(entity);
        personRepository.flush();

        final LocalDateTime dateBeforeModified = entity.getDateModified();

        entity.setLastName("Testing");
        personRepository.save(entity);
        personRepository.flush();

        final LocalDateTime dateAfterModified = entity.getDateModified();

        assertNotEquals(dateBeforeModified, dateAfterModified);
    }

    @Test
    void checkSize() {
        final int size = this.bankService.getAllPersons().size();
        final PersonRequest request = new PersonRequest();
        request.setName("Patrik");
        request.setLastName("Sobol");
        request.setEmail("ju.patrik.sobol@mail.com");
        request.setBankCards(Collections.emptyList());

        this.bankService.savePerson(request);

        assertEquals(size + 1, this.bankService.getAllPersons().size());
    }
}
