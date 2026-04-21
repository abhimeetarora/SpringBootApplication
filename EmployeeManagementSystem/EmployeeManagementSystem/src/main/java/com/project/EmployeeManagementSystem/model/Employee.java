package com.project.EmployeeManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message="Enter correct email")
    private String email;

    @Min(value=1000,message = "Minimum salary is 1000")
    private double salary;
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;
    public Employee()
    {}

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }

    Employee(Long id, String name, String email, double salary)
    {
        this.id=id;
        this.name=name;
        this.email=email;
        this.salary=salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
