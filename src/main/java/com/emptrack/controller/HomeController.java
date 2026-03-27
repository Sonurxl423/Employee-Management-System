package com.emptrack.controller;

import com.emptrack.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EmployeeService service;

    public HomeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String showHome(Model model) {

        long totalEmployees = service.countEmployees();
        long totalHR = service.countByDepartment("HR");
        long totalAdmin = service.countByDepartment("ADMIN");
        long totalDepartments = service.countDistinctDepartments();

        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalHR", totalHR);
        model.addAttribute("totalAdmin", totalAdmin);
        model.addAttribute("totalDepartments", totalDepartments);

        return "homePage";
    }
}