package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireUrlsDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FireUrlsMapper{
   @Autowired
   private CalculateAge calculateAge;

    public FireUrlsDto toDto(Person person){

        FireUrlsDto fireUrlsDto = new FireUrlsDto();

        fireUrlsDto.setLastName(person.getLastName());
        fireUrlsDto.setPhone(person.getPhone());
        List<MedicalRecord> medicalRecordList = person.getMedicalRecord();

        Optional<MedicalRecord> optional = person.getMedicalRecord().stream().findFirst();
        if(optional.isPresent()) {
            String birthdateString = optional.get().getBirthdate();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, dateTimeFormatter);

            int age = calculateAge.calculateAge(birthdate, LocalDate.now());

            fireUrlsDto.setAge(age);

        }List<String> medications = medicalRecordList.stream()
                .map(medicalRecord -> medicalRecord.getMedications())
                .flatMap(medicationList -> medicationList.stream())
                .collect(Collectors.toList());
        fireUrlsDto.setMedications(medications);

        return fireUrlsDto;
    }
}
