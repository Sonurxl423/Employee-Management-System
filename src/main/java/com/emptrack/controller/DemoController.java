package com.emptrack.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/home")
    public String sayhello()
    {
        return "homePage";
    }

    @GetMapping("/fortune")
    public String fortune()
    {
        return "Today is the very lucky day for you !";
    }
}
