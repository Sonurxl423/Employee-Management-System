package com.emptrack.service;

import java.util.List;
import java.util.Optional;

import com.emptrack.dao.EmployeeRepository;
import com.emptrack.entity.Employee;
import com.emptrack.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private final PasswordUtil passwordUtil;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository theEmployeeRepository,PasswordUtil passwordUtil) {
        employeeRepository = theEmployeeRepository;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theEmployee;
    }

    @Override
    public void save(Employee employee) {
        if (!employee.getPassword().startsWith("$2a$")) {
            employee.setPassword(passwordUtil.encode(employee.getPassword()));
        }
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

}






