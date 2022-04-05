package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
@NoArgsConstructor
@AllArgsConstructor

public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("")
    public ResponseEntity<?> postMedicalRecord(@RequestBody MedicalRecordDto medicalRecordDto){
        try{
            log.info("CREATE /medicalRecord");
            medicalRecordService.saveMedicalRecord(medicalRecordDto);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            log.info("CREATE /MedicalRecord error : {}",e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("")
    public  ResponseEntity<?> deleteMedicalRecord (@RequestParam ("firstName") String firstName, @RequestParam ("lastName") String lastName){
        try {
            log.info ("DELETE /MedicalRecord with firstName {} and lastName {}", firstName, lastName);
            medicalRecordService.deleteMedicalRecord(firstName, lastName);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            log.info("DELETE /MedicalRecord with firstname {} and lastname {} error : {}", firstName, lastName, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody MedicalRecordDto medicalRecordDto){
        log.info("UPDATE /MedicalRecord with firstName {} and lastName {}", firstName, lastName);
        try{
            medicalRecordService.updateMedicalRecord(firstName,lastName, medicalRecordDto);
            return ResponseEntity.ok().build();

        }catch (NoSuchElementException e){
            log.info("UPDATE /MedicalRecord with firstName{} and lastName{} error : {}", firstName, lastName, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
