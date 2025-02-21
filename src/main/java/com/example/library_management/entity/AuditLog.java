package com.example.library_management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String methodName;
    private String parameters;
    private String result;
    private String exceptionMessage;
    private LocalDateTime timestamp;

    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    public AuditLog(String methodName, String parameters, String result, String exceptionMessage) {
        this.methodName = methodName;
        this.parameters = parameters;
        this.result = result;
        this.exceptionMessage = exceptionMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
}
