package org.home.tutorial.springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/secured")
@Slf4j
public class SecuredController {

    @GetMapping
    public String unsecured() {
        log.info("Entering secured location");
        return "secured.html";
    }
}
