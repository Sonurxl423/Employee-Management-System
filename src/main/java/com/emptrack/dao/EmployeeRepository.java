package com.emptrack.dao;

import com.emptrack.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // add a method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUsername(String username);

    long countByRole(String role);

    long countByDepartment(String department);

    @Query("SELECT e FROM Employee e " +
            "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Employee> searchEmployees(@Param("keyword") String keyword, Pageable pageable);



}
