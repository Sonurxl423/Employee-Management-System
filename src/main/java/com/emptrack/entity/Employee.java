package com.emptrack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String department;
    private double salary;

    @PrePersist
    @PreUpdate
    public void convertDepartmentToUpperCase() {
        if (this.department != null) {
            this.department = this.department.toUpperCase();
        }
    }
}