package com.example.teamservice.service;

import com.example.teamservice.model.Team;
import com.example.teamservice.repository.TeamRepository;
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

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeams() {
        List<Team> teams = Arrays.asList(new Team(), new Team());
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.getAllTeams();

        assertEquals(2, result.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testGetTeamById_found() {
        Team team = new Team();
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Optional<Team> result = teamService.getTeamById(1L);

        assertTrue(result.isPresent());
        assertEquals(team, result.get());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeamById_notFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Team> result = teamService.getTeamById(1L);

        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveTeam() {
        Team team = new Team();
        when(teamRepository.save(team)).thenReturn(team);

        Team result = teamService.saveTeam(team);

        assertNotNull(result);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void testDeleteTeam() {
        teamService.deleteTeam(1L);

        verify(teamRepository, times(1)).deleteById(1L);
    }
}
