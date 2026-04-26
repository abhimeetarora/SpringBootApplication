package com.project.EmployeeManagementSystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EmployeeManagementSystem.model.Employee;
import com.project.EmployeeManagementSystem.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatService {

    private final EmployeeRepo employeeRepo;

    public ChatService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Map<String, String>> conversation = new ArrayList<>();
    public String getResponse(String prompt) {

        if (prompt.toLowerCase().contains("salary")) {

            String reply = handleSalaryQuery(prompt);

            conversation.add(Map.of(
                    "role", "user",
                    "content", prompt
            ));

            conversation.add(Map.of(
                    "role", "assistant",
                    "content", reply
            ));

            return reply;
        }

        return callAI(prompt);
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
    private String handleSalaryQuery(String prompt) {

        String number = prompt.replaceAll("[^0-9]", "");

        if (number.isEmpty()) {
            return "Please provide a valid employee ID.";
        }

        Long id = Long.parseLong(number);

        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return "Salary of " + emp.getName() + " is " + emp.getSalary();
    }
}