package com.mediscreen.patientService.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="family")
    @NotBlank(message = "Family name cannot be empty.")
    @Length(max=64, message = "Family name is too long.")
    private String familyName;

    @Column(name="given")
    @NotBlank(message = "Given name cannot be empty.")
    @Length(max=64, message = "Given name is too long.")
    private String givenName;

    @Column(name="dob")
    @PastOrPresent(message="Date of birth cannot be in the future")
    @NotNull(message="Date of birth cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^[FM]$", message = "Sex cannot be empty.")
    @NotBlank(message = "Sex cannot be empty.")
    private String sex;

    @NotBlank(message = "Address cannot be empty.")
    @Length(max=255, message = "Address is too long.")
    private String address;

    @NotBlank(message = "Phone number cannot be empty.")
    @Length(max=20, message = "Phone number is too long.")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
