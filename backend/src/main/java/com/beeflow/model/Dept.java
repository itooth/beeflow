package com.beeflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Dept {
    @Id
    private String id;
    private String pid;
    private String name;
} 