package com.openclassrooms.SafetyNetAlerts.service;

import java.util.List;

public interface PhoneAlertService {
    List<String> getPhoneAlert(String station);
}
