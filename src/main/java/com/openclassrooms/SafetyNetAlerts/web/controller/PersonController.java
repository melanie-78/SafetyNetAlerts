package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
}
