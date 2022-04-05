package com.openclassrooms.SafetyNetAlerts.web.dto;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class FloodPersonDto {
    private String lastName;
    private String phone;
    private String address;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
