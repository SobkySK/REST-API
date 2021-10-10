package me.patriksobol.bank.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "bank_card")
public class BankCardEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "card_number")
    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @Column(name = "expiration")
    @NotNull
    private LocalDate expiration;

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }
}
