package com.openclassrooms.SafetyNetAlerts.web.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class FloodDto {
    private Map<String, List<FloodPersonDto>> floodListMap;
}
