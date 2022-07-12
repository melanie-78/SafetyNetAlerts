package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonInfoService {
    List<PersonInfoDto> getPersonInfo(String lastName);
}
