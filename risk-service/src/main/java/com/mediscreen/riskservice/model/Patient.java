package com.mediscreen.riskservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Patient {

    private Long id;

    private String familyName;

    private String givenName;

    private LocalDate dateOfBirth;

    private String sex;

    private String address;

    private String phone;

}
