package com.example.playerservice.controller;

import com.example.playerservice.model.Player;
import com.example.playerservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    void testGetAllPlayers() throws Exception {
        when(playerService.getAllPlayers()).thenReturn(Arrays.asList(new Player(), new Player()));

        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void testGetPlayerById_found() throws Exception {
        Player player = new Player();
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk());

        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testGetPlayerById_notFound() throws Exception {
        when(playerService.getPlayerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isNotFound());

        verify(playerService, times(1)).getPlayerById(1L);
    }

}
