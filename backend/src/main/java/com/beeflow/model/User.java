package com.beeflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "beeflow_user")
public class User {
    @Id
    private String id;
    private String name;
    private String tenantId;
    private String email;
    private String mobile;
    private String avatar;
} 