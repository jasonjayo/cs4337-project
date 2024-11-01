package com.example.eventservice.service;
import com.example.eventservice.repository.EventRepository;

import com.example.eventservice.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

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
            return eventRepository.save(existingEvent);
        } else {
            return null;
        }
    }
}