package com.example.eventservice.repository;

import com.example.eventservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEventIdAndPlayerId(Long eventId, Long playerId);
    List<Attendance> findByEventId(Long eventId);
}
