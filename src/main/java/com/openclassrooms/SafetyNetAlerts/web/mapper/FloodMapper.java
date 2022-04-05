package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FloodMapper {
    public FloodPersonDto toDto (Person person){
        FloodPersonDto floodPersonDto = new FloodPersonDto();

        floodPersonDto.setLastName(person.getLastName());
        floodPersonDto.setPhone(person.getPhone());
        floodPersonDto.setAddress(person.getAddress().getLabel());

        String birthdateString = person.getMedicalRecord().stream().findFirst().get().getBirthdate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

        int age = calculateAge(birthdate, LocalDate.now());
        floodPersonDto.setAge(age);

        List<String> medications = person.getMedicalRecord().stream()
                .map(medicalRecord -> medicalRecord.getMedications())
                .flatMap(medicalRecordList -> medicalRecordList.stream())
                .collect(Collectors.toList());

        floodPersonDto.setMedications(medications);

        List<String> allergies= person.getMedicalRecord().stream()
                .map(medicalRecord -> medicalRecord.getAllergies())
                .flatMap(allergiesList -> allergiesList.stream())
                .collect(Collectors.toList());

        floodPersonDto.setAllergies(allergies);

        return floodPersonDto;
    }

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
