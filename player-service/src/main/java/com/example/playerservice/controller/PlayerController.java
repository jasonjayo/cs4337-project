package com.example.playerservice.controller;

import com.example.playerservice.model.Player;
import com.example.playerservice.repository.PlayerRepository;
import com.example.playerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.shared.utils.AuthUtils.checkToken;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerRepository playerRepository) {
        this.playerService = playerService;
    }

    /**
     * @return a list of all players
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    /**
     * @param id player's ID
     * @return the matching player, if one exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * @param id player's Google ID
     * @return the matching player, if one exists
     */
    @GetMapping("/googleId/{id}")
    public ResponseEntity<Player> getPlayerGoogleId(@PathVariable String id) {
        Optional<Player> player = playerService.getPlayerByGoogleId(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * creates a new player
     *
     * @param player player object
     * @return the created player
     */
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    /**
     * deletes a given player
     *
     * @param id player's ID
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id, @RequestHeader String authorization) {
        if (!checkToken(authorization, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * returns name for a given player
     *
     * @param playerId player's ID
     * @return player's first name, or 404 if player not found
     */
    @GetMapping("/{playerId}/name")
    public ResponseEntity<String> getPlayerName(@PathVariable Long playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);
        return player.map(p -> ResponseEntity.ok(p.getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown"));
    }

    /**
     * add specified player to specified team
     *
     * @param playerId    player's ID
     * @param teamId      team's ID
     * @param requestBody request body containing PIN for team
     * @return boolean representing if player has joined team
     */
    @PostMapping("/{playerId}/teams/{teamId}")
    public boolean addTeamToPlayer(@PathVariable Long playerId, @PathVariable Long teamId, @RequestBody Map<String, String> requestBody, @RequestHeader String authorization) {
        if (!checkToken(authorization, playerId)) {
            return false;
        }
        String pin = requestBody.get("pin");
        return playerService.addPlayerToTeam(playerId, teamId, pin);
    }

    /**
     * remove a player from specified team
     *
     * @param playerId player's ID
     * @param teamId   team's ID
     * @return boolean indicating if removal was successful
     */
    @DeleteMapping("/{playerId}/teams/{teamId}")
    public boolean removeTeamFromPlayer(@PathVariable Long playerId, @PathVariable Long teamId, @RequestHeader String authorization) {
        if (!checkToken(authorization, playerId)) {
            return false;
        }
        return playerService.removePlayerFromTeam(playerId, teamId);
    }

    /**
     * updated the specified player
     *
     * @param id            player's ID
     * @param updatedPlayer the updated player information
     * @return response containing updated player
     */
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer, @RequestHeader String authorization) {
        if (!checkToken(authorization, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Player player = playerService.updatePlayer(id, updatedPlayer);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * get attendance info for player for events
     *
     * @param playerId player's ID
     * @return map of team ID: boolean pairs, each representing if player is attending the given event
     */
    @GetMapping("/{playerId}/events")
    public Map<Long, Boolean> getEventsForPlayer(@PathVariable Long playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);
        return player.get().getEventAttendances();
    }

}
