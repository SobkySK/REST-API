package me.patriksobol.bank.service;

import me.patriksobol.bank.controller.model.PersonRequest;
import me.patriksobol.bank.controller.model.PersonResponse;

import java.util.List;

public interface IBankService {

    void savePerson(PersonRequest person);

    List<PersonResponse> getAllPersons();

    PersonResponse getById(Long id);
}
