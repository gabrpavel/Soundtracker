package com.soundtracker.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-soudtracker/v1")
public class MainController {

    @GetMapping("/home")
    public String home() {
        return "hello";
    }
}
