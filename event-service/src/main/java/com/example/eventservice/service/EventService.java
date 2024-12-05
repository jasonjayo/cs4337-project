package com.example.eventservice.service;

import com.example.eventservice.client.PlayerClient;
import com.example.eventservice.DTO.AttendanceDTO;
import com.example.eventservice.DTO.PlayerDTO;
import com.example.eventservice.model.Attendance;
import com.example.eventservice.repository.AttendanceRepository;
import com.example.eventservice.repository.EventRepository;
import com.example.eventservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private PlayerClient playerClient;

    /**
     * Retrieve all events from the database.
     *
     * @return A list of all events.
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Retrieve an event by its ID.
     *
     * @param id The ID of the event.
     * @return An Optional containing the event if found, or empty if not found.
     */
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    /**
     * Create a new event and save it to the database.
     *
     * @param event The event object to create.
     * @return The created event.
     */
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Delete an event by its ID.
     *
     * @param id The ID of the event to delete.
     */
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Update an existing event with new details.
     *
     * @param id The ID of the event to update.
     * @param updatedEvent The updated event object containing new details.
     * @return The updated event if found, or null if the event does not exist.
     */
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event existingEvent = eventOptional.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setStartTime(updatedEvent.getStartTime());
            existingEvent.setEndTime(updatedEvent.getEndTime());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setEventType(updatedEvent.getEventType());
            return eventRepository.save(existingEvent);
        } else {
            return null;
        }
    }

    /**
     * Update or add a player's attendance status for a specific event.
     *
     * @param eventId The ID of the event.
     * @param playerId The ID of the player.
     * @param status The attendance status (e.g., "true", "false").
     */
    public void updateAttendance(Long eventId, Long playerId, String status) {
        Attendance attendance = attendanceRepository.findByEventIdAndPlayerId(eventId, playerId)
                .orElse(new Attendance(eventId, playerId));
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
    }

    /**
     * Retrieve attendance details for a specific event.
     *
     * @param eventId The ID of the event.
     * @return A list of AttendanceDTO objects containing player and attendance details.
     */
    public List<AttendanceDTO> getAttendanceByEvent(Long eventId) {
        List<Attendance> attendances = attendanceRepository.findByEventId(eventId);
        return attendances.stream()
                .map(attendance -> {
                    String playerName = getPlayerName(attendance.getPlayerId());
                    return new AttendanceDTO(attendance.getPlayerId(), playerName, attendance.getStatus());
                })
                .collect(Collectors.toList());
    }

    /**
     * Helper method to fetch a player's name using the PlayerClient.
     *
     * @param playerId The ID of the player.
     * @return The player's name, or "Unknown" if the name could not be retrieved.
     */
    private String getPlayerName(Long playerId) {
        try {
            return playerClient.getPlayerName(playerId);
        } catch (Exception e) {
            logger.error("Error fetching player name for playerId {}: {}", playerId, e.getMessage());
            return "Unknown";
        }
    }

    /**
     * Retrieve all events associated with a specific team.
     *
     * @param teamId The ID of the team.
     * @return A list of events for the specified team.
     */
    public List<Event> getEventsForTeam(Long teamId) {
        return eventRepository.findByTeamId(teamId);
    }
}
