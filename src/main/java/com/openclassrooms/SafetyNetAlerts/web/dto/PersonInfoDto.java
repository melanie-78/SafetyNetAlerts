package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDto {
    private String lastName;
    private String firstName;
    private String address;
    private String Email;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
