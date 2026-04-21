package com.project.EmployeeManagementSystem.dto;


import com.project.EmployeeManagementSystem.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class DepartmentDTO{
    Long id; String name; List<EmployeeDTO> employeeList; {
    }
}
