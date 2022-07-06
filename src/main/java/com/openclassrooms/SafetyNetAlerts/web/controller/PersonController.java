package com.openclassrooms.SafetyNetAlerts.web.controller;


import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.service.PersonService;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonUpdateDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("")
    public ResponseEntity<?> postPerson(@RequestBody PersonDto personDto){
        log.info("CREATE /person");
        personService.savePerson(personDto);
        return ResponseEntity.ok().build();
    }


    @PutMapping("")
    public ResponseEntity<?> putPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody PersonUpdateDto personUpdateDto){
        log.info("PUT /person with firstname {} and lastname {}", firstName, lastName);
        try {
            personService.update(firstName,lastName,personUpdateDto);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementException e){
            log.error("PUT /person with firstname {} and lastname {} error : {}", firstName, lastName, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("")
    public ResponseEntity<?> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        log.info("DELETE /person with firstname {} and lastname {}", firstName, lastName);
     try {
         personService.deletePerson(firstName, lastName);
         return ResponseEntity.ok().build();
     } catch (NoSuchElementException e) {
         log.error("DELETE /person with firstname {} and lastname {} error : {}", firstName, lastName, e.getMessage());
         return ResponseEntity.notFound().build();
     }

    }

}
