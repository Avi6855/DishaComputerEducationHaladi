package com.avinashpatil.app.DishaComputerEducationHaladi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String dishaId;

    private String fullName;

    private String email;


    @ManyToMany(mappedBy = "enrolledUsers")
    private List<Course> courses;

    @ManyToMany(mappedBy = "students")
    private List<Batch> batches;

    @OneToMany(mappedBy = "user")
    private List<Certificate> certificates;

    @PrePersist
    public void generateDishaId() {
        if (this.dishaId == null) {
            this.dishaId = "Disha-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }

}
