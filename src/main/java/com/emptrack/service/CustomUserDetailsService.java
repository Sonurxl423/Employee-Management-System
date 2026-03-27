package com.emptrack.service;

import com.emptrack.dao.EmployeeRepository;
import com.emptrack.entity.Employee;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository repo;

    public CustomUserDetailsService(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // username = email
        Employee emp = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(emp.getEmail())
                .password(emp.getPassword())
                .roles(emp.getRole().toUpperCase()) // ROLE mapping
                .build();
    }
}