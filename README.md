# 🚀 Employee Management System (Spring Boot + JWT + AWS Ready)

## 📌 Overview

This project is a **Spring Boot REST API** for managing employees with:

* 🔐 Authentication using Spring Security
* 🔑 JWT-based authorization
* 👥 Role-based access control (ADMIN / USER)
* 🗄️ Database integration using JPA & Hibernate

---

## 🧠 Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Hibernate / JPA
* MySQL
* Maven

---

## 🔥 Features

* ✅ User Registration & Login
* 🔐 Password Encryption (BCrypt)
* 🎟️ JWT Token Generation
* 🛡️ Secure APIs using JWT Filter
* 👮 Role-based Authorization (`@PreAuthorize`)
* 📦 DTO Mapping
* 🔄 CRUD Operations on Employees

---

## 🔐 Authentication Flow

```text
User → Login API
        ↓
AuthenticationManager
        ↓
UserDetailsService (fetch user from DB)
        ↓
Password matches (BCrypt)
        ↓
JWT Token generated
```

---

## 🔑 Authorization Flow (JWT)

```text
Client Request → Authorization Header (Bearer Token)
        ↓
JWT Filter
        ↓
Extract Username
        ↓
Validate Token
        ↓
Set Authentication in SecurityContext
        ↓
Controller Access Granted / Denied
```

---

## 👮 Role-Based Access

Example:

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteEmployee(Long id)
```

### Behavior:

| Role       | Access            |
| ---------- | ----------------- |
| ROLE_ADMIN | ✅ Allowed         |
| ROLE_USER  | ❌ Forbidden (403) |

---

## 🧩 Project Structure

```text
com.project.EmployeeManagementSystem
│
├── controller       → REST APIs
├── service          → Business logic
├── repository       → Database access
├── model            → Entities
├── dto              → Data transfer objects
├── exception        → Custom exceptions
├── security         → JWT + Security config
```

---

## 🔐 Security Configuration

* Stateless authentication (no sessions)
* JWT filter added before authentication filter
* Public endpoints:

```text
/auth/**
```

* Secured endpoints:

```text
/employees/**
```

---

## 🧪 API Endpoints

### 🔹 Auth

| Method | Endpoint    | Description     |
| ------ | ----------- | --------------- |
| POST   | /auth       | Register user   |
| POST   | /auth/login | Login & get JWT |

---

### 🔹 Employee

| Method | Endpoint        | Access        |
| ------ | --------------- | ------------- |
| GET    | /employees      | Authenticated |
| DELETE | /employees/{id} | ADMIN only    |

---

## 🧠 Key Concepts Covered

* Spring Security Basics
* Authentication vs Authorization
* JWT Token Flow
* Custom UserDetailsService
* Password Encoding (BCrypt)
* Filter Chain in Spring Security
* Method-level Security (`@PreAuthorize`)

---

## ⚠️ Common Issues Faced (and Solved)

* ❌ 403 due to missing Bearer token
* ❌ Basic Auth overriding JWT
* ❌ Password mismatch (BCrypt issue)
* ❌ JWT filter not registered
* ❌ ROLE_ prefix mismatch

---

## ☁️ Deployment (Planned)

* AWS EC2 deployment
* Run JAR on cloud instance
* Public API access

---

## 🚀 Future Enhancements

* AWS deployment
* Chatbot integration (LLM)
* Caching (`@Cacheable`)
* Logging improvements

---

## 🎯 Learning Outcome

This project demonstrates:

```text
End-to-end backend development with security,
real-world authentication flow, and scalable design.
```

---
 Author

**Abhimeet Arora**

---
