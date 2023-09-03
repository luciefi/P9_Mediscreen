package com.mediscreen.webapp.model;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserCreate {

    @NotBlank(message = "Login cannot be empty.")
    @Length(max = 64, message = "Login is too long.")
    private String login;

    @NotBlank(message = "Password cannot be empty.")
    @Length(max = 64, message = "Password is too long.")
    private String password;

    @Pattern(regexp="^(ROLE_USER|ROLE_ADMIN)$",message="Role is invalid.")
    @NotBlank(message = "Role cannot be empty.")
    private String role;

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
