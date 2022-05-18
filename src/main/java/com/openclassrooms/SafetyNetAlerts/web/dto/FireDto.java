package com.openclassrooms.SafetyNetAlerts.web.dto;


import com.openclassrooms.SafetyNetAlerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FireDto {
    private List<String> stations;
    private List<FireUrlsDto> fireUrlsDtoList;
}
