package com.openclassrooms.SafetyNetAlerts.web.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplCommunityEmailService;
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
@RequestMapping("/communityemail")

public class CommunityEmailController {
    @Autowired
    private ImplCommunityEmailService communityEmailService;

    @GetMapping("")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") String city){
        try{
            log.info("GET /communityEmail with city {} ", city);
            List<String> communityEmail = communityEmailService.getCommunityEmail(city);
            return ResponseEntity.ok(communityEmail);
        }catch(NoSuchElementException e){
            log.error("GET /communityEmail with city {} error: ", city, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
