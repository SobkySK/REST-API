package me.patriksobol.bank.service;

import me.patriksobol.bank.controller.exception.ObjectAlreadyExistsException;
import me.patriksobol.bank.controller.exception.ObjectNotFoundException;
import me.patriksobol.bank.controller.model.PersonRequest;
import me.patriksobol.bank.controller.model.PersonResponse;
import me.patriksobol.bank.domain.entity.BankCardEntity;
import me.patriksobol.bank.domain.entity.PersonEntity;
import me.patriksobol.bank.repository.BankCardRepository;
import me.patriksobol.bank.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankService implements IBankService {

    private final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    private final ModelMapper mapper;
    private final PersonRepository personRepository;
    private final BankCardRepository bankCardRepository;

    public BankService(
            final ModelMapper mapper,
            final PersonRepository personRepository,
            final BankCardRepository bankCardRepository
    ) {
        this.mapper = mapper;
        this.personRepository = personRepository;
        this.bankCardRepository = bankCardRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "persons", allEntries = true)
    public void savePerson(final PersonRequest person) {
        LOGGER.debug("BankService: `savePerson` | Start");

        final Optional<PersonEntity> o_personEntity = personRepository.getByEmail(person.getEmail());
        if (o_personEntity.isPresent()) {
            LOGGER.debug("BankService: `savePerson` | Error: `Person already exists!`");
            throw new ObjectAlreadyExistsException("Person already exists!");
        }

        if (person.getBankCards()
                .stream()
                .anyMatch(card -> bankCardRepository.existsBankCardEntityByCardNumber(card.getCardNumber()))) {
            LOGGER.debug("BankService: `savePerson` | Error: `One of card already exists!`");
            throw new ObjectAlreadyExistsException("One of card already exists!");
        }

        final PersonEntity personEntity = new PersonEntity();
        personEntity.setName(person.getName());
        personEntity.setLastName(person.getLastName());
        personEntity.setEmail(person.getEmail());

        final Set<BankCardEntity> cardEntities = person.getBankCards()
                .stream()
                .map(card -> {
                    final BankCardEntity cardEntity = new BankCardEntity();
                    cardEntity.setCardNumber(card.getCardNumber());
                    cardEntity.setExpiration(card.getExpiration());
                    cardEntity.setPerson(personEntity);
                    return cardEntity;
                })
                .collect(Collectors.toSet());

        personRepository.save(personEntity);
        bankCardRepository.saveAll(cardEntities);

        LOGGER.debug("BankService: `savePerson` | Success: `Person saved.`");
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "persons")
    public List<PersonResponse> getAllPersons() {
        LOGGER.debug("BankService: `getAllPersons` | Start");

        final List<PersonResponse> result = personRepository.findAll()
                .stream()
                .map(person -> mapper.map(person, PersonResponse.class))
                .collect(Collectors.toList());

        LOGGER.debug("BankService: `getAllPersons` | Success");

        return result;
    }

    @Override
    @Cacheable(value = "person", key = "#id")
    @Transactional(readOnly = true)
    public PersonResponse getById(final Long id) {
        LOGGER.debug("BankService: `getById` | Start");

        final Optional<PersonEntity> o_personEntity = personRepository.findById(id);
        if (o_personEntity.isEmpty()) {
            LOGGER.debug(String.format("BankService: `getById` | Error: `%s`", id));
            throw new ObjectNotFoundException(String.format("Person with id %d does not exist!", id));
        }

        final PersonEntity person = o_personEntity.get();
        final PersonResponse result = mapper.map(person, PersonResponse.class);

        LOGGER.debug("BankService: `getById` | Success");

        return result;
    }
}
