package com.emptrack.service;

import java.util.List;
import java.util.Optional;

import com.emptrack.dao.EmployeeRepository;
import com.emptrack.entity.Employee;
import com.emptrack.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private final PasswordUtil passwordUtil;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository theEmployeeRepository,PasswordUtil passwordUtil) {
        employeeRepository = theEmployeeRepository;
        this.passwordUtil = passwordUtil;
    }

    public Page<Employee> findPaginated(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee findById(Long theId) {
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

        //  Validate unique email
        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());

        if (existing.isPresent() &&
                (employee.getId() == null ||
                        !existing.get().getId().equals(employee.getId()))) {

            throw new RuntimeException("Email already exists!");
        }

        //  HANDLE PASSWORD PROPERLY
        if (employee.getId() != null) {

            Employee existingEmp = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // If password is empty → keep old password
            if (employee.getPassword() == null || employee.getPassword().isBlank()) {
                employee.setPassword(existingEmp.getPassword());
            }
            // If new plain password → encode
            else if (!employee.getPassword().startsWith("$2a$")) {
                employee.setPassword(passwordUtil.encode(employee.getPassword()));
            }

        } else {
            //  New employee
            employee.setPassword(passwordUtil.encode(employee.getPassword()));
        }

        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public long countEmployees() {
        return employeeRepository.count();
    }


    @Override
    public long countByRole(String Role) {
        return employeeRepository.countByRole(Role);
    }

    @Override
    public long countByDepartment(String department) {
        return employeeRepository.countByDepartment(department);
    }

    @Override
    public long countDistinctDepartments() {
        return employeeRepository.findAll()
                .stream()
                .map(Employee::getDepartment)
                .distinct()
                .count();
    }

    public Page<Employee> searchEmployeesPaginated(String keyword, Pageable pageable) {
        return employeeRepository.searchEmployees(keyword, pageable);
    }

}






