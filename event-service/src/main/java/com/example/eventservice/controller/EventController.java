package com.example.eventservice.controller;

import com.example.eventservice.DTO.AttendanceDTO;
import com.example.eventservice.model.Event;
import com.example.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.shared.utils.AuthUtils.checkToken;


@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * Retrieve all events along with their attendance statistics.
     * @return A map containing the list of events and their corresponding attendance stats.
     */
    @GetMapping
    public Map<String, Object> getAllEvents() {
        // Fetch all events
        List<Event> events = eventService.getAllEvents();

        // Prepare attendance statistics for each event
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

    /**
     * Retrieve an event by its ID.
     * @param id The ID of the event.
     * @return The event if found, or a 404 status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new event.
     * @param event The event object to create.
     * @return The created event.
     */
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    /**
     * Update an existing event by its ID.
     * @param id The ID of the event to update.
     * @param updatedEvent The updated event details.
     * @return The updated event, or a 404 status if the event is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an event by its ID.
     * @param id The ID of the event to delete.
     * @return A 204 status if the deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update or mark a player's attendance status for a specific event.
     * @param eventId The ID of the event.
     * @param playerId The ID of the player.
     * @param status The attendance status ("true" or "false").
     * @return A success message upon updating the attendance.
     */
    @PostMapping("/{eventId}/attendance/{playerId}")
    public ResponseEntity<String> updateAttendance(
            @PathVariable Long eventId,
            @PathVariable Long playerId,
            @RequestParam("status") String status,
            @RequestHeader String authorization) {
        if (!checkToken(authorization, playerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        eventService.updateAttendance(eventId, playerId, status);
        return ResponseEntity.ok("Attendance status updated successfully.");
    }

    /**
     * Retrieve the attendance list for a specific event.
     * @param eventId The ID of the event.
     * @return A list of attendance records for the event.
     */
    @GetMapping("/{eventId}/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEvent(@PathVariable Long eventId) {
        List<AttendanceDTO> attendanceList = eventService.getAttendanceByEvent(eventId);
        return ResponseEntity.ok(attendanceList);
    }

    /**
     * Retrieve all events associated with a specific team.
     * @param teamId The ID of the team.
     * @return A list of events for the team.
     */
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Event>> getEventsForTeam(@PathVariable Long teamId) {
        List<Event> events = eventService.getEventsForTeam(teamId);
        return ResponseEntity.ok(events);
    }
}
