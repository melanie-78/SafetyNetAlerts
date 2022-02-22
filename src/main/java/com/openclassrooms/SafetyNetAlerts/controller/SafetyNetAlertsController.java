package com.openclassrooms.SafetyNetAlerts.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class SafetyNetAlertsController {

    @GetMapping("/world")
    public String helloWorld() {
        return "Salut Mélanie !";
    }

    @GetMapping("/moon")
    public String helloMoon() {

        return "Salut Youssef !";
    }

    @GetMapping("/person")
    public String helloMoon(@RequestParam("name") String name) {
        return "Salut " + name + " !";
    }

    /*
    @GetMapping("/firestation")
    @GetMapping("/childAlert")
    @GetMapping("/phoneAlert")
    @GetMapping("/fire")
    @GetMapping("/flood")
    @GetMapping("/personInfo")
    @GetMapping("/communityEmail")

    @PostMapping("/person")
    @PutMapping("/person")
    @DeleteMapping("/person")

    @PostMapping("/firestation")
    @PutMapping("/firestation")
    @DeleteMapping("/firestation")

    @PostMapping("/medicalRecord")
    @PutMapping("/medicalRecord")
    @DeleteMapping("/medicalRecord")
     */
}
