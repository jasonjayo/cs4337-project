package com.example.eventservice.service;

import com.example.eventservice.DTO.AttendanceDTO;
import com.example.eventservice.client.PlayerClient;
import com.example.eventservice.model.Attendance;
import com.example.eventservice.model.Event;
import com.example.eventservice.repository.AttendanceRepository;
import com.example.eventservice.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private PlayerClient playerClient;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testGetEventById_found() {
        Event event = new Event();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals(event, result.get());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEventById_notFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(1L);

        assertFalse(result.isPresent());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateEvent() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertNotNull(result);
        verify(eventRepository, times(1)).save(event);
    }
}
