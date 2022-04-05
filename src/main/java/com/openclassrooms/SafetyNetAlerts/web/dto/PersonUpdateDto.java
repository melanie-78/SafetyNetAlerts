package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateDto {
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
