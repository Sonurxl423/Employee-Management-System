package com.emptrack.controller;

import com.emptrack.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final EmployeeService service;

    public DashboardController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        long totalEmployees = service.countEmployees();

        model.addAttribute("totalEmployees", totalEmployees);

        return "dashboard";
    }
}