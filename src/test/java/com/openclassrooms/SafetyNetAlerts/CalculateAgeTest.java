package com.openclassrooms.SafetyNetAlerts;

import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculateAgeTest {
    @InjectMocks
    private CalculateAge ca;

    @Test
    public void calculateAgeTest(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse("07/16/1990", dateTimeFormatter);
        LocalDate currentDate = LocalDate.now();
        int expected = 31;

        int actual = ca.calculateAge(birthDate, currentDate);

        assertEquals(expected, actual);
    }
}
