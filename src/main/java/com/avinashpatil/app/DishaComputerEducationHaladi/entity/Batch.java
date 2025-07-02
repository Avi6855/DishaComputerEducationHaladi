package com.avinashpatil.app.DishaComputerEducationHaladi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "course_batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String batchId;

    private String batchNo;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime timing;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batch")
    private List<Quiz> quizzes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batch")
    private List<Exam> exams;

    @ManyToMany
    @JoinTable(
            name = "batch_users",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> students;
}
