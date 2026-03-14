package com.emptrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LeaderController {

    @GetMapping("/leaders")
    public String leaderPage()
    {
        return "leaders";
    }
}
