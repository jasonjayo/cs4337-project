package com.example.playerservice.controller;

import com.example.playerservice.model.Player;
import com.example.playerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/googleId/{id}")
    public ResponseEntity<Player> getPlayerGoogleId(@PathVariable String id) {
        Optional<Player> player = playerService.getPlayerByGoogleId(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{playerId}/name")
    public ResponseEntity<String> getPlayerName(@PathVariable Long playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);
        return player.map(p -> ResponseEntity.ok(p.getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown"));
    }

    @PostMapping("/{playerId}/teams/{teamId}")
    public boolean addTeamToPlayer(@PathVariable Long playerId, @PathVariable Long teamId, @RequestBody Map<String, String> requestBody) {
        String pin = requestBody.get("pin");
        return playerService.addPlayerToTeam(playerId, teamId, pin);
    }

}
