package com.example.playerservice.service;

import com.example.playerservice.model.Player;
import com.example.playerservice.repository.PlayerRepository;
import com.example.shared.dto.TeamDTO;
import com.example.shared.feign.TeamClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamClient teamClient;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetAllPlayers() {
//        List<Player> players = Arrays.asList(new Player(), new Player());
//        when(playerRepository.findAll()).thenReturn(players);
//
//        List<Player> result = playerService.getAllPlayers();
//
//        assertEquals(2, result.size());
//        verify(playerRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetPlayerById_found() {
//        Player player = new Player();
//        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
//
//        Optional<Player> result = playerService.getPlayerById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(player, result.get());
//        verify(playerRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testGetPlayerById_notFound() {
//        when(playerRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Optional<Player> result = playerService.getPlayerById(1L);
//
//        assertFalse(result.isPresent());
//        verify(playerRepository, times(1)).findById(1L);
//    }
}
