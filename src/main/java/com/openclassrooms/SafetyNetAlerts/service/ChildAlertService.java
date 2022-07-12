package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.web.dto.ChildAlertDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChildAlertService {
    List<ChildAlertDto> getChildAlert(String address);
}
