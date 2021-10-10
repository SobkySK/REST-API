package me.patriksobol.bank.controller;

import me.patriksobol.bank.controller.model.PersonRequest;
import me.patriksobol.bank.controller.model.PersonResponse;
import me.patriksobol.bank.controller.model.SuccessResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface BankController {

    @PostMapping(path="/save")
    @ResponseBody
    SuccessResponse savePerson(@RequestBody @Valid PersonRequest person);

    @GetMapping(path = "/list")
    @ResponseBody List<PersonResponse> getAll();

    @GetMapping(path = "/find/{id}")
    @ResponseBody PersonResponse getById(@PathVariable Long id);
}
