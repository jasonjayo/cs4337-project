package com.example.eventservice.controller;

import com.example.eventservice.DTO.AttendanceDTO;
import com.example.eventservice.model.Event;
import com.example.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public Map<String, Object> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        List<Map<String, Integer>> attendanceStats = new ArrayList<>();
        for (Event event : events) {
            List<AttendanceDTO> attendances = eventService.getAttendanceByEvent(event.getEvent_id());
            long attendingCount = attendances.stream()
                    .filter(attendance -> attendance.getStatus().equals("true"))
                    .count();
            long notAttendingCount = attendances.size() - attendingCount;
            attendanceStats.add(Map.of(
                    "attending", (int) attendingCount,
                    "notAttending", (int) notAttendingCount
            ));
        }
        return Map.of(
                "events", events,
                "attendanceStats", attendanceStats
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to update or mark a player's attendance status for a specific event
    @PostMapping("/{eventId}/attendance/{playerId}")
    public ResponseEntity<String> updateAttendance(
            @PathVariable Long eventId,
            @PathVariable Long playerId,
            @RequestParam("status") String status) {
        eventService.updateAttendance(eventId, playerId, status);
        return ResponseEntity.ok("Attendance status updated successfully.");
    }

    // Endpoint to retrieve attendance for a specific event
    @GetMapping("/{eventId}/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEvent(@PathVariable Long eventId) {
        List<AttendanceDTO> attendanceList = eventService.getAttendanceByEvent(eventId);
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Event>> getEventsForTeam(@PathVariable Long teamId) {
        List<Event> events = eventService.getEventsForTeam(teamId);
        return ResponseEntity.ok(events);
    }
}
