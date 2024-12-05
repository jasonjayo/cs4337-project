package com.example.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

/**
 * Entity class representing the Attendance table in the database.
 * This class maps to a database table and holds attendance information for events.
 */
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

    /**
     * Custom constructor to initialize an Attendance object with eventId and playerId.
     * Sets default values for status and timestamp.
     * 
     * @param eventId The ID of the event.
     * @param playerId The ID of the player.
     */
    public Attendance(Long eventId, Long playerId) {
        this.eventId = eventId;
        this.playerId = playerId;
        this.status = "Pending"; // or any default value for status if needed
        this.timestamp = new Timestamp(System.currentTimeMillis()); // or any default timestamp
    }
}
