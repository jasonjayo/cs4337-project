package com.example.eventservice.controller;

import com.example.eventservice.DTO.AttendanceDTO;
import com.example.eventservice.model.Event;
import com.example.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
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
}
