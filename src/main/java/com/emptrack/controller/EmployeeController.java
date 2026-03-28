package com.emptrack.controller;

import com.emptrack.entity.Employee;
import com.emptrack.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping("/list")
    public String listEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Page<Employee> employeePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            employeePage = employeeService.searchEmployeesPaginated(keyword, PageRequest.of(page, size));
        } else {
            employeePage = employeeService.findPaginated(PageRequest.of(page, size));
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());

        return "employees/list-employees";
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @GetMapping("/Add")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }


    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/Update")
    public String showFormForUpdate(@RequestParam("employeeId") Long id, Model themodel) {

        Employee theEmployee = employeeService.findById(id);
        themodel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") Long theId) {

        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/employees/list";

    }
}









