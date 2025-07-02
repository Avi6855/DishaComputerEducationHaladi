package com.avinashpatil.app.DishaComputerEducationHaladi.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String examId;

    private String title;
    private LocalDateTime scheduledDateTime;
    private int durationMinutes;
    private boolean isSample;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToMany
    private List<Question> questions;
}
