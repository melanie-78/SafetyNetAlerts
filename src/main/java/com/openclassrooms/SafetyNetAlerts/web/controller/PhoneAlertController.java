package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplPhoneAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/phonealert")
public class PhoneAlertController {
    @Autowired
    private ImplPhoneAlertService phoneAlertService;

    @GetMapping("")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam("station") String station){
        try{
            log.info("GET /PhoneAlert with station {} ", station);
            List<String> phoneAlert = phoneAlertService.getPhoneAlert(station);
            return ResponseEntity.ok(phoneAlert);
        }catch (NoSuchElementException e){
            log.error("GET /PhoneAlert with station {} error : {} ", station, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
