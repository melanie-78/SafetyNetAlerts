package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireUrlsDto {
    private String lastName;
    private int age;
    private String phone;
    private List<String> medications;
}
