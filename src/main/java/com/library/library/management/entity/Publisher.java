package com.library.library.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // 0 = active, 1 = deleted (soft delete)
    @Column(nullable = false)
    private Integer status = 0;

    public Publisher() {}

    public Publisher(String name) {
        this.name = name;
        this.status = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
