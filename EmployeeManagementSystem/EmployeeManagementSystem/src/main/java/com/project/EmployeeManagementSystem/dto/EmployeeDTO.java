package com.project.EmployeeManagementSystem.dto;

import com.project.EmployeeManagementSystem.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private String email;
    private String departmentName;
}
