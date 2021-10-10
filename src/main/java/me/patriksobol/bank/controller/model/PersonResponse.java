package me.patriksobol.bank.controller.model;

import javax.validation.Valid;
import java.util.List;

public class PersonResponse extends AbstractPerson {

    private Long id;

    @Valid
    private List<BankCardResponse> bankCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BankCardResponse> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCardResponse> bankCards) {
        this.bankCards = bankCards;
    }
}
