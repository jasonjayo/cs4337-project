package com.example.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "events", schema = "dbo")

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "team_sheet", columnDefinition = "TEXT")
    private String teamSheet;

    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "team_id")

    private Long teamId;
}