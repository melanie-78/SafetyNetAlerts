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
public class FloodController {
    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public List<FloodDto> getFlood(@RequestParam("stations") List<Integer> stations){
        try {
            log.info("GET /flood with station {}", stations);
            return this.floodService.getFlood(stations);
        }catch (NoSuchElementException e){
            log.info("GET /flood with station {} error : {} ", stations, e.getMessage());
            return null;
        }
    }
}
