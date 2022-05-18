package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FireStationUrlsInfosDto {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int age;
}
