package com.mediscreen.webapp.model;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

// TODO Database annotation
public class User {

    @NotBlank(message = "Login cannot be empty.")
    @Length(max=20, message = "Login is too long.")
    private String login;

    @NotBlank(message = "Password cannot be empty.")
    @Length(max=20, message = "Password is too long.")
    private String password;

    private String role; // TODO admin & staff  / role ->  pas String mais authrity qui est un modele/table à créer

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

}
