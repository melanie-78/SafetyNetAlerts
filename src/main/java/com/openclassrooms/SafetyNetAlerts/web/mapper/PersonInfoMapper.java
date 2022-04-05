package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class PersonInfoMapper {
    public PersonInfoDto toDto (Person person){
        PersonInfoDto personInfoDto = new PersonInfoDto();

        personInfoDto.setLastName(person.getLastName());
        personInfoDto.setFirstName(person.getFirstName());
        personInfoDto.setEmail(person.getEmail());

        String address = person.getAddress().getLabel();
        personInfoDto.setAddress(address);

        Optional<MedicalRecord> optional = person.getMedicalRecord().stream().findFirst();
        if (optional.isPresent()) {
            String birthdateString = optional.get().getBirthdate();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

            int age = calculateAge(birthdate, LocalDate.now());
            personInfoDto.setAge(age);
        }

        List<String> medications = person.getMedicalRecord()
                .stream()
                .map(medicalRecord -> medicalRecord.getMedications())
                .flatMap(medicationList -> medicationList.stream())
                .collect(Collectors.toList());
        personInfoDto.setMedications(medications);

        List<String> allergies = person.getMedicalRecord()
                .stream()
                .map(medicalRecord -> medicalRecord.getAllergies())
                .flatMap(allergiesList -> allergiesList.stream())
                .collect(Collectors.toList());
        personInfoDto.setAllergies(allergies);

        return personInfoDto;
    }

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
