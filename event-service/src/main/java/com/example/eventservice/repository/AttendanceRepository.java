package com.example.eventservice.repository;

import com.example.eventservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Find a specific attendance record by event ID and player ID.
     * 
     * @param eventId The ID of the event.
     * @param playerId The ID of the player.
     * @return An Optional containing the Attendance if found, or empty if not found.
     */
    Optional<Attendance> findByEventIdAndPlayerId(Long eventId, Long playerId);

    /**
     * Find all attendance records for a specific event.
     * 
     * @param eventId The ID of the event.
     * @return A list of Attendance records associated with the specified event.
     */
    List<Attendance> findByEventId(Long eventId);
}
