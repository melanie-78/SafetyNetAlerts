package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsInfosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
public class FireStationUrlsMapper {
    @Autowired
    private CalculateAge calculateAge;

    public FireStationUrlsInfosDto toDto(Person person){
        FireStationUrlsInfosDto fireStationUrlsInfosDto = new FireStationUrlsInfosDto();

        fireStationUrlsInfosDto.setFirstName(person.getFirstName());
        fireStationUrlsInfosDto.setLastName(person.getLastName());
        fireStationUrlsInfosDto.setAddress(person.getAddress().getLabel());
        fireStationUrlsInfosDto.setPhone(person.getPhone());

        Optional<MedicalRecord> optional = person.getMedicalRecord().stream().findFirst();
        if(optional.isPresent()) {
            String birthdateString = optional.get().getBirthdate();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

            int age = calculateAge.calculateAge(birthdate, LocalDate.now());

            fireStationUrlsInfosDto.setAge(age);
        }
        return fireStationUrlsInfosDto;
    }
}
