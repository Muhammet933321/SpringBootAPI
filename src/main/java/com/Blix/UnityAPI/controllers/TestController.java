package com.Blix.UnityAPI.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RestController
    class HelloworldController {
        @GetMapping("/test")
        String hello() {
            return "Deneme !!";

        }

    }
}
