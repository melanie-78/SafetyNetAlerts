package com.openclassrooms.SafetyNetAlerts.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityEmailService{
    List<String> getCommunityEmail(String city);
}
