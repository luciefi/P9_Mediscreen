package com.mediscreen.webapp.model;


import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
public class User {

    @Id
    @NotBlank(message = "Login cannot be empty.")
    @Length(max = 64, message = "Login is too long.")
    private String login;

    @Length(max = 64, message = "Password is too long.")
    private String password;

    @Pattern(regexp="^(ROLE_USER|ROLE_ADMIN)$",message="Role is invalid.")
    @NotBlank(message = "Role cannot be empty.")
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creationdate")
    private LocalDate creationDate;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

}
