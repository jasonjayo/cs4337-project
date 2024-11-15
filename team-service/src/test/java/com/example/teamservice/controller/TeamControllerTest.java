package com.example.teamservice.controller;

import com.example.teamservice.model.Team;
import com.example.teamservice.service.TeamService;
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

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    void testGetAllTeams() throws Exception {
        when(teamService.getAllTeams()).thenReturn(Arrays.asList(new Team(), new Team()));

        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(teamService, times(1)).getAllTeams();
    }

    @Test
    void testGetTeamById_found() throws Exception {
        Team team = new Team();
        when(teamService.getTeamById(1L)).thenReturn(Optional.of(team));

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isOk());

        verify(teamService, times(1)).getTeamById(1L);
    }

    @Test
    void testGetTeamById_notFound() throws Exception {
        when(teamService.getTeamById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isNotFound());

        verify(teamService, times(1)).getTeamById(1L);
    }

    @Test
    void testSaveTeam() throws Exception {
        Team team = new Team();
        when(teamService.saveTeam(any(Team.class))).thenReturn(team);

        mockMvc.perform(post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Sample Team\"}"));
    }
}