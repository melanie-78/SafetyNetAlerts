package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.FireService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireDto;
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
@RequestMapping("/fire")
public class FireController {
    @Autowired
    private FireService fireService;

    @GetMapping("")
    public ResponseEntity<List<FireDto>> getFire(@RequestParam("address") String address){
        log.info("GET /fire with address {} ", address);
        try {
            List<FireDto> fire = fireService.getFire(address);
            return ResponseEntity.ok().body(fire);
        }catch (NoSuchElementException e){
            log.error("GET /fire with address {} error : {}", address, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
