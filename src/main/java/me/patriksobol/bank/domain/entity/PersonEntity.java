package me.patriksobol.bank.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "person")
public class PersonEntity extends AbstractEntity {

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 20)
    private String lastName;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @OneToMany(targetEntity = BankCardEntity.class, mappedBy = "person", fetch = FetchType.LAZY)
    private Set<BankCardEntity> bankCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<BankCardEntity> getBankCards() {
        return bankCards;
    }

    public void setBankCards(Set<BankCardEntity> bankCards) {
        this.bankCards = bankCards;
    }
}
