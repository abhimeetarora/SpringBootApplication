package com.project.EmployeeManagementSystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EmployeeManagementSystem.dto.EmployeeDTO;
import com.project.EmployeeManagementSystem.exception.ResourceNotFoundException;
import com.project.EmployeeManagementSystem.model.Employee;
import com.project.EmployeeManagementSystem.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeService employeeService;

    public ChatService(EmployeeRepo employeeRepo, EmployeeService employeeService) {
        this.employeeRepo = employeeRepo;
        this.employeeService=employeeService;
    }

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Map<String, String>> conversation = new ArrayList<>();
    public String getResponse(String prompt) {

        String lower = prompt.toLowerCase();
        if (lower.contains("each") || lower.contains("all")) {
            return formatAllEmployees(employeeService.getAllEmployees());
        }
        if (lower.contains("salary")) {
            return handleSalary(prompt);
        }


        if (lower.contains("details") || lower.contains("info")) {
            return handleEmployeeDetails(prompt);
        }

        if (lower.contains("all employees") || lower.contains("list")) {
            return handleAllEmployees();
        }

        return callAI(prompt);
    }

    private String formatAllEmployees(List<EmployeeDTO> allEmployees) {
        if (allEmployees.isEmpty())
            return "no employee found";
        StringBuilder sb=new StringBuilder();
        for(EmployeeDTO dto:allEmployees)
        {
            sb.append("name: ").append(dto.getName()).append("email: ").append(dto.getEmail()).append("\n");
        }
        return sb.toString();
    }


    private String callAI(String prompt) {

        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        conversation.add(Map.of(
                "role", "user",
                "content", prompt
        ));

        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of(
                "role", "system",
                "content", "You are an assistant for an Employee Management System. Help with employee-related queries."
        ));

        messages.addAll(conversation);
        Map<String, Object> requestMap = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", messages
        );

        String body;
        try {
            body = objectMapper.writeValueAsString(requestMap);
        } catch (Exception e) {
            return "Error creating request";
        }

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());

            String reply = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
            conversation.add(Map.of(
                    "role", "assistant",
                    "content", reply
            ));

            return reply;

        } catch (Exception e) {
            return "Error parsing response";
        }
    }
    private String handleSalary(String prompt) {

        String number = prompt.replaceAll("[^0-9]", "");

        if (number.isEmpty()) {
            return "Please provide a valid employee ID.";
        }

        Long id = Long.parseLong(number);

        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found enter a valid Id"));

        String reply = "Salary of " + emp.getName() + " is " + emp.getSalary();

        saveToConversation(prompt, reply);

        return reply;
    }
    private String handleEmployeeDetails(String prompt) {

        String number = prompt.replaceAll("[^0-9]", "");

        if (number.isEmpty()) {
            return "Please provide a valid employee ID.";
        }

        Long id = Long.parseLong(number);

        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Details not found"));

        String reply = """
            Employee Details:
            Name: %s
            Salary: %s
            """.formatted(emp.getName(), emp.getSalary());

        saveToConversation(prompt, reply);

        return reply;
    }
    private String handleAllEmployees() {

        List<Employee> employees = employeeRepo.findAll();

        if (employees.isEmpty()) {
            return "No employees found.";
        }

        StringBuilder sb = new StringBuilder("Employees:\n");

        for (Employee e : employees) {
            sb.append("ID: ").append(e.getId())
                    .append(", Name: ").append(e.getName())
                    .append("\n");
        }

        String reply = sb.toString();

        saveToConversation("list employees", reply);

        return reply;
    }
    private void saveToConversation(String user, String reply) {
        conversation.add(Map.of("role", "user", "content", user));
        conversation.add(Map.of("role", "assistant", "content", reply));
    }
}