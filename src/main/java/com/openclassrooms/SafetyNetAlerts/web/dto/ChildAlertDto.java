package com.openclassrooms.SafetyNetAlerts.web.dto;

import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonAgeMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ChildAlertDto {
    private List<PersonAgeDto> childList;
    private List<PersonAgeDto> adultList;
}
