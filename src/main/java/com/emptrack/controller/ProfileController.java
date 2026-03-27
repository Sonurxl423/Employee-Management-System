package com.emptrack.controller;

import com.emptrack.dao.EmployeeRepository;
import com.emptrack.entity.Employee;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    private final EmployeeRepository repo;

    public ProfileController(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {

        String input = authentication.getName();

        // 🔥 Handle both username OR email login
        Optional<Employee> empOpt = repo.findByEmail(input);

        if (empOpt.isEmpty()) {
            empOpt = repo.findByUsername(input);
        }

        Employee emp = empOpt
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("employee", emp);

        return "profile";
    }
}