package com.emptrack.service;

import com.emptrack.entity.Employee;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    void save(Employee theEmployee);

    void deleteById(int theId);

    public Page<Employee> findPaginated(Pageable pageable);

    long countEmployees();

}
