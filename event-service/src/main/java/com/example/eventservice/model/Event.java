package com.example.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * Entity class representing the Event table in the database.
 * This class maps to a database table and stores event-related details.
 */
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
    private LocalDate eventDate;
    private String location;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String teamSheet;
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;
    @Column(name = "team_id")
    private Long teamId;
}