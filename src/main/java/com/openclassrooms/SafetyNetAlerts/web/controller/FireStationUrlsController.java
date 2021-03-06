package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplFireStationUrlsService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsDto;
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
@RequestMapping("/firestation")
public class FireStationUrlsController {
    @Autowired
    private ImplFireStationUrlsService fireStationUrlsService;

    @GetMapping("")
    public ResponseEntity<List<FireStationUrlsDto>> getFireStationUrls(@RequestParam("station") String station){
        try {
            log.info("GET / firestationurls with station {} ", station);
            List<FireStationUrlsDto> fireStationUrls = fireStationUrlsService.getFireStationUrls(station);
            return ResponseEntity.ok().body(fireStationUrls);
        }catch (NoSuchElementException e){
            log.error("GET / firestationurls with station {} error: {} ", station, e.getMessage());
            return  ResponseEntity.notFound().build();
        }
    }
}
