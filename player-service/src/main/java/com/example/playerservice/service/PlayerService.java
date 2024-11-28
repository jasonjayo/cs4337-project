package com.example.playerservice.service;

import com.example.shared.dto.TeamDTO;
import com.example.playerservice.model.Player;
import com.example.playerservice.repository.PlayerRepository;
import com.example.shared.feign.TeamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    @Autowired
    private TeamClient teamClient;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {

        List<Player> players = playerRepository.findAll();

        players.forEach(player -> {
            Set<Long> teamIds = player.getTeamIds();

            // Fetch team details for each team ID
            List<TeamDTO> teams = teamIds.stream()
                    .map(teamClient::getTeamById) // Call team-service for each team ID
                    .collect(Collectors.toList());

            // Set the fetched team objects in the transient field
            player.setTeams(teams);
        });

        return players;

    }

    public Optional<Player> getPlayerById(Long playerId) {
        Optional<Player> player = playerRepository.findById(playerId);

        if (player.isPresent()) {
            Set<Long> teamIds = player.get().getTeamIds();

            List<TeamDTO> teams = teamIds.stream()
                    .map(teamClient::getTeamById) // Call team-service for each team ID
                    .collect(Collectors.toList());

            // Set the fetched team objects in the transient field
            player.get().setTeams(teams);
        }
        return Optional.of(player.get());
    }

    public Optional<Player> getPlayerByGoogleId(String googleId) {
        return playerRepository.findByGoogleId(googleId);
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public boolean addPlayerToTeam(Long playerId, Long teamId, String pin) {
        TeamDTO team = teamClient.getTeamById(teamId);

        if (team.getPin().equals(pin)) {
            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new RuntimeException("Player not found"));

            player.getTeamIds().add(teamId);
            playerRepository.save(player);

            return true;
        }
        return false;
    }

    public boolean removePlayerFromTeam(Long playerId, Long teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        player.getTeamIds().remove(teamId);
        playerRepository.save(player);

        return true;
    }

}
