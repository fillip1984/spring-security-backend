package org.home.tutorial.springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/unsecured")
@Slf4j
public class UnsecuredController {

    @GetMapping
    public String unsecured() {
        log.info("Entering unsecured location");
        return "unsecured.html";
    }
}
