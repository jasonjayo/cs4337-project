package com.example.shared.feign;

import com.example.shared.dto.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * Feign Client. This allows services that need to get data from the player service to do so.
 */
@FeignClient(name = "player-service")
public interface PlayerClient {
    /**
     * This allows other services to use the createPlayer method provided by the player service.
     * Any service using this client can call createPlayer, and behind the scenes this client will connect to the
     * player service and call its createPlayer. These connections are handled for us thanks to the use of this
     * Feign client. Behind the scenes, we're making use of the player REST API.
     *
     * @param playerDTO player data transfer object representing the player to create
     * @return the player DTO we provided
     */
    @PostMapping("/api/players")
    PlayerDTO createPlayer(@RequestBody PlayerDTO playerDTO);

    /**
     * Similarly, this allows us to use the getPlayerByGoogleId functionality provided by the player service.
     *
     * @param id player's Google ID
     * @return player DTO for the player, if found
     */
    @GetMapping("/api/players/googleId/{id}")
    PlayerDTO getPlayerByGoogleId(@PathVariable String id);

}
