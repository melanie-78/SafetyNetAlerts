package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Component
public class PersonAgeMapper {

    public PersonAgeDto toDto(Person person) {

        PersonAgeDto personAgeDto = new PersonAgeDto();

        personAgeDto.setFirstName(person.getFirstName());
        personAgeDto.setLastName(person.getLastName());

        Optional<MedicalRecord> optional = person.getMedicalRecord().stream().findFirst();
        if (optional.isPresent()) {
            String birthdateString = optional.get().getBirthdate();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

            int age = calculateAge(birthdate, LocalDate.now());
            personAgeDto.setAge(age);

            personAgeDto.setAge(age);
        }
        return personAgeDto;
    }
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}

