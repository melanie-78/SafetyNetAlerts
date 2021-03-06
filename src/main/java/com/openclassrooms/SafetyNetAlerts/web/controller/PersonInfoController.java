package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.service.ImplPersonInfoService;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
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
@RequestMapping("/personinfo")
public class PersonInfoController {
    @Autowired
    private ImplPersonInfoService personInfoService;

    @GetMapping("")
    public ResponseEntity<List<PersonInfoDto>> getPersonInfo(@RequestParam("lastName") String lastName){
        try{
            log.info("GET /personInfo with lastName {}", lastName);
            List<PersonInfoDto> personInfo = personInfoService.getPersonInfo(lastName);
            return ResponseEntity.ok(personInfo);
        }catch (NoSuchElementException e){
            log.error("GET /personInfo with lastName {} error : {} ", lastName, e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

}
