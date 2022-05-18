package com.openclassrooms.SafetyNetAlerts.web.dto;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAgeDto {
    private String firstName;
    private String lastName;
    private int age;
}
