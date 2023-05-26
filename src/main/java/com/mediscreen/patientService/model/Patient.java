package com.mediscreen.patientService.model;

import org.aspectj.lang.annotation.Before;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="family")
    @NotBlank(message = "Family name cannot be empty.")
    private String familyName;

    @Column(name="given")
    @NotBlank(message = "Given name cannot be empty.")
    private String givenName;

    @Column(name="dob")
    @PastOrPresent
    private LocalDate dateOfBirth;

    @Pattern(regexp = "[FM]$")
    private String sex;

    @NotBlank(message = "Adress cannot be empty.")
    private String address;

    @NotBlank(message = "Phone number cannot be empty.")
    private String phone;

}
