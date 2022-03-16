package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.service.FireStationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

    private FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;

    }
}

