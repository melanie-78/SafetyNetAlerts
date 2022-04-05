package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.FloodService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/flood")
@NoArgsConstructor
@AllArgsConstructor

public class FloodController {
    @Autowired
    private FloodService floodService;

    @GetMapping("")
    public List<FloodDto> getFlood(@RequestParam("station") String station){
        try {
            log.info("GET /flood with station {}", station);
            return this.floodService.getFlood(station);
        }catch (NoSuchElementException e){
            log.info("GET /flood with station {} error : {} ", station, e.getMessage());
            return null;
        }
    }
}
