package com.project.EmployeeManagementSystem.service;

import com.project.EmployeeManagementSystem.dto.*;
import com.project.EmployeeManagementSystem.exception.ResourceNotFoundException;
import com.project.EmployeeManagementSystem.model.Department;
import com.project.EmployeeManagementSystem.model.Employee;
import com.project.EmployeeManagementSystem.repository.DepartmentRepo;
import com.project.EmployeeManagementSystem.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
   public EmployeeRepo employeeRepo;
   public DepartmentRepo departmentRepo;

    EmployeeService(EmployeeRepo employeeRepo,DepartmentRepo departmentRepo)
    {
        this.departmentRepo=departmentRepo;
        this.employeeRepo=employeeRepo;
    }
 public   Employee createEmployee(Employee emp)
    {
if(emp.getDepartment()!=null)
{
    Long depid=emp.getDepartment().getId();
    Department department=departmentRepo.findById(depid).orElseThrow(()->new ResourceNotFoundException("Department not found"));
    emp.setDepartment(department);
}
        return employeeRepo.save(emp);
    }

    public DepartmentDTO getDepartment(Long id)
    {
        DepartmentDTO dto=new DepartmentDTO();
        Department department=departmentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("id not  found"));
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployeeList(department.getEmployees().stream().map(employee->
            new EmployeeDTO(employee.getId(), employee.getName(),employee.getEmail(),employee.getDepartment().getName()))
        .toList());
        return dto;
    }
  public  List<EmployeeDTO> getEmployeewithDepeartment()
    {
       List<Employee> employees=employeeRepo.getDepartment();
       return employees.stream().map(emp->new EmployeeDTO(
               emp.getId(), emp.getName(), emp.getEmail(), emp.getDepartment().getName()

       )).toList();
    }
  public EmployeeDTO getEmployeeById(Long id)
    {

        EmployeeDTO dto=new EmployeeDTO();
        Employee employee=employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        return dto;
    }
    @Transactional
    public UpdateDTO updateEmployee(Long id,UpdateDTO updateDTO)
    {
        Employee employee=employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Id not found"));
        employee.setName(updateDTO.name());
        employee.setEmail(updateDTO.email());
        employee.setSalary(updateDTO.salary());
        Employee saved=employeeRepo.save(employee);
        UpdateDTO dto=new UpdateDTO(saved.getName(),saved.getEmail(),saved.getSalary());
        return dto;
    }
   @PreAuthorize("hasRole('ADMIN')")
    public void deleteEmployee(Long id)
    {

        employeeRepo.deleteById(id);
    }
    @Transactional
    public PatchEmployeeDTO patchUpdate(Long id, PatchEmployeeDTO patchEmployeeDTO)
    {
        Employee employee=employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("id not found"));
        if(patchEmployeeDTO.name()!=null)
            employee.setName(patchEmployeeDTO.name());
        if(patchEmployeeDTO.email()!=null)
            employee.setEmail(patchEmployeeDTO.email());
        if(patchEmployeeDTO.salary()!=null)
            employee.setSalary(patchEmployeeDTO.salary());
        Employee saved=employeeRepo.save(employee);
        PatchEmployeeDTO dto=new PatchEmployeeDTO(saved.getName(),saved.getEmail(),saved.getSalary());
        return  dto;

    }
    public int updateSalary(Long id, double salary)
    {
        int updated=employeeRepo.updateSalary(id,salary);
        if(salary==0)
        {
            throw new ResourceNotFoundException("employee not found");
        }
        return  updated;

    }

    public  int deleteEmp(Long id)
    {
        int deleted=employeeRepo.deleteEmp(id);
        if(deleted==0)
        {
            throw new ResourceNotFoundException("id not found");
    }return  deleted;
    }
  public List<EmployeeDTO> getAllEmployees()
    {
        List<Employee> employee=employeeRepo.findAll();
        return  employee.stream().map(emp->{
            EmployeeDTO dto=new EmployeeDTO();
            dto.setId(emp.getId());
            dto.setName(emp.getName());
            dto.setEmail(emp.getEmail());
            return dto;
        }).toList();

    }
    public Optional<Employee> getByEmail(String email)
    {
        return employeeRepo.getByEmail(email);
    }

    public PageResponse<EmployeeDTO> getEmployees(int page, int size, String sortBy) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        Page<Employee> employeePage=employeeRepo.findAll(pageable);
        List<EmployeeDTO> employeeDto=employeePage.getContent().stream().map(employee -> {EmployeeDTO dto=new EmployeeDTO();
            dto.setEmail(employee.getEmail());
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            return dto;}).toList();
            PageResponse<EmployeeDTO> pageResponse= new PageResponse<>(employeeDto,employeePage.getNumber(),employeePage.getSize(),employeePage.getTotalElements(),employeePage.getTotalPages());
            return pageResponse;
    }
}
