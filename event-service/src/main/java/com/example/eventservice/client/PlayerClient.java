package com.example.eventservice.client;

import com.example.eventservice.DTO.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client to interact with the Player Service.
 * Used to make HTTP requests to the `player-service` microservice.
 */
@FeignClient(name = "player-service")
public interface PlayerClient {

    /**
     * Fetch detailed information about a player by their ID.
     * @param playerId The ID of the player to fetch.
     * @return A PlayerDTO object containing player details.
     */
    @GetMapping("/api/players/{playerId}")
    PlayerDTO getPlayerById(@PathVariable("playerId") Long playerId);

    /**
     * Fetch the name of a player by their ID.
     * @param playerId The ID of the player to fetch.
     * @return The name of the player as a String.
     */
    @GetMapping("/api/players/{playerId}/name")
    String getPlayerName(@PathVariable("playerId") Long playerId);
}