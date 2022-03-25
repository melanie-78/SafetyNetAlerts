package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/all")
    public List<PersonDto> getAllPersons(){
        return this.personService.getAllPersons();
     }

    @PutMapping("")
    public ResponseEntity<?> putPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody PersonDto personDto){
        log.info("PUT /person with firstname {} and lastname {}", firstName, lastName);
        try {
            personService.update(firstName,lastName,personDto);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementException e){
            log.info("PUT /person with firstname {} and lastname {} error : {}", firstName, lastName, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postPerson(@RequestBody PersonDto personDto){
        log.info("CREATE /person");
        personService.savePerson(personDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        log.info("DELETE /person with firstname {} and lastname {}", firstName, lastName);
     try {
         personService.deletePerson(firstName, lastName);
         return ResponseEntity.ok().build();
     } catch (NoSuchElementException e) {
         log.info("DELETE /person with firstname {} and lastname {} error : {}", firstName, lastName, e.getMessage());
         return ResponseEntity.notFound().build();
     }

    }

}
