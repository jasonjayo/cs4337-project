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

    /**
     * @return list of all players. Team IDs are replaced with a list of team objects, so team info is also included
     */
    public List<Player> getAllPlayers() {

        List<Player> players = playerRepository.findAll();

        // get list of teams for each player
        players.forEach(player -> {
            Set<Long> teamIds = player.getTeamIds();

            // get team details for each team ID
            List<TeamDTO> teams = teamIds.stream()
                    .map(teamClient::getTeamById) // using team-service via team feign client
                    .collect(Collectors.toList());

            player.setTeams(teams);
        });

        return players;

    }

    /**
     * get a single player by ID. Includes player's team information.
     *
     * @param playerId palyer's ID
     * @return player's information, if found, including info. on teams they've joined
     */
    public Optional<Player> getPlayerById(Long playerId) {
        Optional<Player> player = playerRepository.findById(playerId);

        if (player.isPresent()) {
            Set<Long> teamIds = player.get().getTeamIds();

            List<TeamDTO> teams = teamIds.stream()
                    .map(teamClient::getTeamById) // using team-service via team feign client
                    .collect(Collectors.toList());

            player.get().setTeams(teams);

            return player;

        } else {
            return Optional.empty();
        }
    }

    /**
     * finds player by Google ID
     *
     * @param googleId player's Google ID
     * @return player, if found
     */
    public Optional<Player> getPlayerByGoogleId(String googleId) {
        return playerRepository.findByGoogleId(googleId);
    }

    /**
     * save a player's information to the repository
     *
     * @param player player object
     * @return the given player object
     */
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * update a player
     *
     * @param id            player's ID
     * @param updatedPlayer updated player
     * @return the updated player
     */
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player existingPlayer = playerOptional.get();
            existingPlayer.setPlayerName(updatedPlayer.getPlayerName());
            return playerRepository.save(existingPlayer);
        } else {
            return null;
        }
    }

    /**
     * deletes player
     *
     * @param playerId player's ID
     */
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    /**
     * adds player to team (causes update to player_team table)
     *
     * @param playerId player's ID
     * @param teamId   team ID
     * @param pin      PIN for team
     * @return boolean indicating if player was successfully added to team
     */
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

    /**
     * removes player from team (causes update to player_team table)
     *
     * @param playerId player's ID
     * @param teamId   team's ID
     * @return boolean indicating if player was successfully removed from team
     */
    public boolean removePlayerFromTeam(Long playerId, Long teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        player.getTeamIds().remove(teamId);
        playerRepository.save(player);

        return true;
    }

}
