package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FloodMapper {
    @Autowired
    private CalculateAge calculateAge;

    public FloodPersonDto toDto (Person person){
        FloodPersonDto floodPersonDto = new FloodPersonDto();

        floodPersonDto.setLastName(person.getLastName());
        floodPersonDto.setPhone(person.getPhone());
        floodPersonDto.setAddress(person.getAddress().getLabel());

        Optional<MedicalRecord> optional = person.getMedicalRecord().stream().findFirst();
        if(optional.isPresent()) {
            String birthdateString = optional.get().getBirthdate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

            int age = calculateAge.calculateAge(birthdate, LocalDate.now());
            floodPersonDto.setAge(age);
        }

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
}
