package me.patriksobol.bank.controller.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class PersonRequest extends AbstractPerson {

    @NotEmpty(message = "Bank card info is missing.")
    @Valid
    private List<BankCardRequest> bankCards;

    public List<BankCardRequest> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCardRequest> bankCards) {
        this.bankCards = bankCards;
    }
}
