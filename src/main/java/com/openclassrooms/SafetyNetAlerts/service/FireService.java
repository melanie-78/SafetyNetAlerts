package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.web.dto.FireDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FireService {
    List<FireDto> getFire(String address);
}
