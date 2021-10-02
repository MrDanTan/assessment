package com.recruitment.avalog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiceRollController {

    @GetMapping(value = "/test", produces = "text/html")
    public String testMethod() {
        return "Hello !";
    }
}
