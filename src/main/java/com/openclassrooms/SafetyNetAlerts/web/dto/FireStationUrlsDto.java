package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor

public class FireStationUrlsDto {
    private List<FireStationUrlsInfosDto> fireStationUrlsInfosList;
    private int adultsNumber;
    private int childrenNumber;
}
