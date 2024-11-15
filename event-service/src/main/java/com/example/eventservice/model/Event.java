package com.example.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;
    private String eventType;
    private LocalDateTime eventDate;
    private String location;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String teamSheet;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "team_id", nullable = false)
    private Long teamId;
}