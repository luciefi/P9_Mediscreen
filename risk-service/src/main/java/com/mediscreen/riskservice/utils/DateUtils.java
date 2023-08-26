package com.mediscreen.riskservice.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateUtils {

    public static int getAge(LocalDate dateOfBirth){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
