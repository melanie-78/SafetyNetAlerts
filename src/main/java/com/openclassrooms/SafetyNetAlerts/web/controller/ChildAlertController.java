package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.ChildAlertService;
import com.openclassrooms.SafetyNetAlerts.web.dto.ChildAlertDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@RequestMapping("/childalert")
public class ChildAlertController {
    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping("")
    public ResponseEntity<List<ChildAlertDto>> getChildAlert(@RequestParam("address") String address){
        try{
            log.info("GET /childalert with address {} ", address);
            List<ChildAlertDto> childAlert = childAlertService.getChildAlert(address);
            return ResponseEntity.ok().body(childAlert);

        }catch (NoSuchElementException e){
            log.info("GET /childalert with address {} error :{}" , address, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
