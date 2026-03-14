package com.emptrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/systems")
    public String systemPage()
    {
        return "systems";
    }
}
