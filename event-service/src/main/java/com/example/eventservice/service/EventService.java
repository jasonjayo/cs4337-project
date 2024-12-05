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

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

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

    public void updateAttendance(Long eventId, Long playerId, String status) {
        Attendance attendance = attendanceRepository.findByEventIdAndPlayerId(eventId, playerId)
                .orElse(new Attendance(eventId, playerId));
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
    }

    public List<AttendanceDTO> getAttendanceByEvent(Long eventId) {
        List<Attendance> attendances = attendanceRepository.findByEventId(eventId);
        return attendances.stream()
                .map(attendance -> {
                    String playerName = getPlayerName(attendance.getPlayerId());
                    return new AttendanceDTO(attendance.getPlayerId(), playerName, attendance.getStatus());
                })
                .collect(Collectors.toList());
    }

    //TODO: fix name retrieval
    private String getPlayerName(Long playerId) {
        try {
            return playerClient.getPlayerName(playerId);
        } catch (Exception e) {
            logger.error("Error fetching player name for playerId {}: {}", playerId, e.getMessage());
            return "Unknown";
        }
    }

    public List<Event> getEventsForTeam(Long teamId) {
        return eventRepository.findByTeamId(teamId);
    }
}
