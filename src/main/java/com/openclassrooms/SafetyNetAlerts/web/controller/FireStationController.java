package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.service.ImplFireStationService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private ImplFireStationService fireStationService;


    @PostMapping("")
    public ResponseEntity<?> saveFireStation(@RequestBody FireStationDto fireStationDto) {
    log.info("CREATE  /firestation");
    fireStationService.saveFireStation(fireStationDto);
    return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteFireStation(@RequestBody FireStationDto fireStationDto){
        log.info("DELETE /firestation");
        try{
            fireStationService.deleteFireStation(fireStationDto);
            return ResponseEntity.ok().build();
        }catch(NoSuchElementException e){
            log.error("DELETE /firestation error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateFireStation(@RequestBody FireStationUpdateDto firestationUpdateDto) {
        log.info("UPDATE /firestation with address {} ", firestationUpdateDto.getAddress());
        try {
            fireStationService.updateFireStation(firestationUpdateDto);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("UPDATE /firestation with address {} error : {}", firestationUpdateDto.getAddress(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}

