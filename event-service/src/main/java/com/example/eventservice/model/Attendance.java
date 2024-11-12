package com.example.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @Column(nullable = false)
    private Long playerId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false)
    private Timestamp timestamp;

    // Custom constructor for specific fields
    public Attendance(Long eventId, Long playerId) {
        this.eventId = eventId;
        this.playerId = playerId;
        this.status = "Pending"; // or any default value for status if needed
        this.timestamp = new Timestamp(System.currentTimeMillis()); // or any default timestamp
    }
}
