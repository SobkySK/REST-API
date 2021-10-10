package me.patriksobol.bank.controller.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class AbstractPerson {

    @NotBlank(message = "Empty person's name!")
    @Size(max = 20, message = "Wrong length of person's name! Max 20 characters are allowed.")
    private String name;

    @NotBlank(message = "Empty person's lastname!")
    @Size(max = 20, message = "Wrong length of person's lastname! Max 20 characters are allowed.")
    private String lastName;

    @NotBlank(message = "Empty person's email!")
    @Email(message = "Wrong format of email address!")
    private String email;

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
}
