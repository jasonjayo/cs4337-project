package com.example.eventservice.client;

import com.example.eventservice.DTO.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "player-service")
public interface PlayerClient {

    @GetMapping("/api/players/{playerId}")
    PlayerDTO getPlayerById(@PathVariable("playerId") Long playerId);

    @GetMapping("/api/players/{playerId}/name")
    String getPlayerName(@PathVariable("playerId") Long playerId);
}