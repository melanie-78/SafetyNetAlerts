package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirestationUpdateDto {
    private String address;
    private String station;
    private String newStation;
}
