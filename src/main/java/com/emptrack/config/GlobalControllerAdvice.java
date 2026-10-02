package com.emptrack.config;

import com.emptrack.dao.EmployeeRepository;
import com.emptrack.entity.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final EmployeeRepository repo;

    public GlobalControllerAdvice(EmployeeRepository repo) {
        this.repo = repo;
    }

    @ModelAttribute("loggedInUser")
    public Employee getLoggedInUser(Authentication authentication) {

        if (authentication == null) return null;

        String loginValue = authentication.getName();

        // login can be email or username
        Optional<Employee> emp = repo.findByEmail(loginValue);

        if (emp.isEmpty()) {
            emp = repo.findByUsername(loginValue);
        }

        return emp.orElse(null);
    }
}