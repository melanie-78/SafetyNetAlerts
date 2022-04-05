package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsInfosDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


@Component
public class FireStationUrlsMapper {
    public FireStationUrlsInfosDto toDto(Person person){
        FireStationUrlsInfosDto fireStationUrlsBisDto = new FireStationUrlsInfosDto();

        fireStationUrlsBisDto.setFirstName(person.getFirstName());
        fireStationUrlsBisDto.setLastName(person.getLastName());
        fireStationUrlsBisDto.setAddress(person.getAddress().getLabel());
        fireStationUrlsBisDto.setPhone(person.getPhone());

        String birthdateString = person.getMedicalRecord().stream().findFirst().get().getBirthdate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

        int age = calculateAge(birthdate, LocalDate.now());

        fireStationUrlsBisDto.setAge(age);

        return fireStationUrlsBisDto;
    }


    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
