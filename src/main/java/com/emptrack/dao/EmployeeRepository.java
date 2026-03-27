package com.emptrack.dao;

import com.emptrack.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // that's it ... no need to write any code LOL!

    // add a method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);

}
