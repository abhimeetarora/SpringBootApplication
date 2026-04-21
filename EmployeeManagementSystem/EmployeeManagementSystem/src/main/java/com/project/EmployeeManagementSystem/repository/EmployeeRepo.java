package com.project.EmployeeManagementSystem.repository;

import com.project.EmployeeManagementSystem.dto.EmployeeDTO;
import com.project.EmployeeManagementSystem.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo  extends JpaRepository<Employee,Long>
{

    Optional<Employee> getByEmail(String email);
    @Query("select e from Employee e JOIN FETCH e.department")
    List<Employee> getDepartment();

    @Modifying
    @Transactional
    @Query("update Employee e set e.salary=:salary where e.id=:id")
    int updateSalary(Long id,Double salary);

    @Modifying
    @Transactional
    @Query("Delete Employee e where e.id=:id")
    int deleteEmp(Long id);
}
