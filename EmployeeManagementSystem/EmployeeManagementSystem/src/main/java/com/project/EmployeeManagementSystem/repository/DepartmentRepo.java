package com.project.EmployeeManagementSystem.repository;

import com.project.EmployeeManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {
}
