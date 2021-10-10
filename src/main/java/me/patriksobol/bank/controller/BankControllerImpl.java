package me.patriksobol.bank.controller;

import me.patriksobol.bank.controller.exception.ObjectAlreadyExistsException;
import me.patriksobol.bank.controller.model.AbstractBankCard;
import me.patriksobol.bank.controller.model.PersonRequest;
import me.patriksobol.bank.controller.model.PersonResponse;
import me.patriksobol.bank.controller.model.SuccessResponse;
import me.patriksobol.bank.service.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BankControllerImpl implements BankController {

    private final Logger LOGGER = LoggerFactory.getLogger(BankControllerImpl.class);

    private final IBankService bankService;

    public BankControllerImpl(
            final IBankService bankService
    ) {
        this.bankService = bankService;
    }

    @Override
    public SuccessResponse savePerson(final PersonRequest person) {
        LOGGER.debug("Endpoint: `/save` | Start");

        if (person.getBankCards()
                .stream()
                .collect(Collectors.groupingBy(AbstractBankCard::getCardNumber, Collectors.counting()))
                .entrySet().stream().anyMatch(set -> set.getValue() > 1)) {
            LOGGER.debug("Endpoint: `/save` | Error: `At least, two card numbers equals.`");
            throw new ObjectAlreadyExistsException("At least, two card numbers equals.");
        }

        this.bankService.savePerson(person);

        LOGGER.debug("Endpoint: `/save` | Success");

        return new SuccessResponse("Person successfully saved!");
    }

    @Override
    public List<PersonResponse> getAll() {
        LOGGER.debug("Endpoint: `/list` | Start");

        final List<PersonResponse> response = bankService.getAllPersons();

        LOGGER.debug("Endpoint: `/list` | Success");

        return response;
    }

    @Override
    public PersonResponse getById(final Long id) {
        LOGGER.debug("Endpoint: `/find/{id}` | Start");

        final PersonResponse response = bankService.getById(id);

        LOGGER.debug("Endpoint: `/find/{id}` | Success");

        return response;
    }
}
