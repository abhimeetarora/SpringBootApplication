package com.project.EmployeeManagementSystem.controller;

import com.project.EmployeeManagementSystem.dto.*;
import com.project.EmployeeManagementSystem.model.Department;
import com.project.EmployeeManagementSystem.model.Employee;
import com.project.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }



    @GetMapping
    public PageResponse<EmployeeDTO> getAllEmployees(
            @RequestParam(defaultValue =" 0") int page,
            @RequestParam (defaultValue  ="3")int size,
            @RequestParam(defaultValue = "id")String sortBy) {

        return employeeService.getEmployees(page, size,sortBy);
    }
    @GetMapping("/departments/{id}")
    public DepartmentDTO getDepartment(@PathVariable Long id) {
        return employeeService.getDepartment(id);
    }
    @GetMapping("/with-department")
    public List<EmployeeDTO> getEmployeeFromDepartment()
    {
        return  employeeService.getEmployeewithDepeartment();
    }
    @PutMapping("/{id}")
    public UpdateDTO updateEmployee(@PathVariable Long id,@RequestBody UpdateDTO updateDTO)
    {
        return employeeService.updateEmployee(id,updateDTO);
    }
    @PutMapping("/salary/{id}")
    public ResponseEntity<String> updateSalary(@PathVariable Long id, @RequestParam double salary)
    {
         employeeService.updateSalary(id, salary);
         return ResponseEntity.ok("salary updated");
    }
    @DeleteMapping("/deletequery/{id}")
    public  ResponseEntity<Void> deleteEmp(@PathVariable Long id)
    {
         employeeService.deleteEmp(id);
         return  ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id)
    {
        employeeService.deleteEmployee(id);
    }
    @PatchMapping("/{id}")
    public PatchEmployeeDTO updatePatch(@PathVariable Long id,@RequestBody PatchEmployeeDTO dto)
    {
        return  employeeService.patchUpdate(id,dto);
    }

    @GetMapping("/{id}")
    EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/email/{email}")
    Optional<Employee> getByEmail(@PathVariable String email) {
        return employeeService.getByEmail(email);
    }
}
