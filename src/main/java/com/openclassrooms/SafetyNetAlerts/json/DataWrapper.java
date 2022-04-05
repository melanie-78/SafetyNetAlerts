package com.openclassrooms.SafetyNetAlerts.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class DataWrapper {

    private List<PersonDto> persons;

    @JsonProperty("firestations")
    private List<FireStationDto> fireStations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecordDto> medicalRecords;
}
