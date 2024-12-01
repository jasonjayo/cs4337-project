package com.example.playerservice.controller;

import com.example.playerservice.model.Player;
import com.example.playerservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        Player player1 = new Player();
        player1.setPlayerId(1L);
        player1.setPlayerName("John Doe");

        Player player2 = new Player();
        player2.setPlayerId(2L);
        player2.setPlayerName("Jane Smith");

        List<Player> mockPlayers = Arrays.asList(player1, player2);
        when(playerService.getAllPlayers()).thenReturn(mockPlayers);

        // Act
        List<Player> players = playerController.getAllPlayers();

        // Assert
        assertNotNull(players);
        assertEquals(2, players.size());
        assertEquals("John Doe", players.get(0).getPlayerName());
        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void testGetPlayerById_PlayerExists() {
        // Arrange
        Player player = new Player();
        player.setPlayerId(1L);
        player.setPlayerName("John Doe");
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        // Act
        ResponseEntity<Player> response = playerController.getPlayerById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getPlayerName());
        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testGetPlayerById_PlayerNotFound() {
        // Arrange
        when(playerService.getPlayerById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Player> response = playerController.getPlayerById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testCreatePlayer() {
        // Arrange
        Player player = new Player();
        player.setPlayerName("New Player");
        when(playerService.savePlayer(any(Player.class))).thenReturn(player);

        // Act
        ResponseEntity<Player> response = playerController.createPlayer(player);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Player", response.getBody().getPlayerName());
        verify(playerService, times(1)).savePlayer(any(Player.class));
    }

    @Test
    void testDeletePlayer() {
        // Arrange
        doNothing().when(playerService).deletePlayer(1L);

        // Act
        ResponseEntity<Void> response = playerController.deletePlayer(1L);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(playerService, times(1)).deletePlayer(1L);
    }
}
