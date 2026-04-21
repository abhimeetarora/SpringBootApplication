package com.project.EmployeeManagementSystem.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }

}
