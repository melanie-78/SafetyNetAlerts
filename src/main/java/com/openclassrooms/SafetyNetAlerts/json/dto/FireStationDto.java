package com.openclassrooms.SafetyNetAlerts.json.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationDto {
    private String address;
    private String station;
}
