package com.emptrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer

                        // Everyone (EMPLOYEE, HR, ADMIN) can view list
                        .requestMatchers("/employees/list")
                        .hasAnyRole("EMPLOYEE", "HR", "ADMIN")

                        // Only HR & ADMIN can add/update
                        .requestMatchers("/employees/showFormForAdd", "/employees/save", "/employees/showFormForUpdate")
                        .hasAnyRole("HR", "ADMIN")

                        // Only ADMIN can delete
                        .requestMatchers("/employees/delete")
                        .hasRole("ADMIN")

                        // Allow static resources (important)
                        .requestMatchers("/css/**", "/js/**")
                        .permitAll()

                        // All other requests require login

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}