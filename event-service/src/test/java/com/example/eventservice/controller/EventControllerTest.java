package com.example.eventservice.controller;

import com.example.eventservice.model.Event;
import com.example.eventservice.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void testGetAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(new Event(), new Event()));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testGetEventById_found() throws Exception {
        Event event = new Event();
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk());

        verify(eventService, times(1)).getEventById(1L);
    }

    @Test
    void testGetEventById_notFound() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isNotFound());

        verify(eventService, times(1)).getEventById(1L);
    }

    @Test
    void testCreateEvent() throws Exception {
        Event event = new Event();
        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Provide JSON content for the event if necessary
                .andExpect(status().isOk());

        verify(eventService, times(1)).createEvent(any(Event.class));
    }
}
