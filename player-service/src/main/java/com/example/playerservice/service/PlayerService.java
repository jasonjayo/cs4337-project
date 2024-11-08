package com.example.playerservice.service;

import com.example.playerservice.dto.TeamDTO;
import com.example.playerservice.model.Player;
import com.example.playerservice.repository.PlayerRepository;
import com.example.playerservice.client.TeamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private TeamClient teamClient;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public List<TeamDTO> getAllTeams() {
        List<TeamDTO> teams = teamClient.getAllTeams();
        logger.info("Fetched teams from Team Client: {}", teams); // Log the fetched teams
        return teams;
    }

}
