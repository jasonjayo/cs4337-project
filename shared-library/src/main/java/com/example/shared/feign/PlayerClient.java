package com.example.shared.feign;

import com.example.shared.dto.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "player-service")
public interface PlayerClient {
    @PostMapping("/api/players")
    PlayerDTO createPlayer(@RequestBody PlayerDTO playerDTO);

    @GetMapping("/api/players/googleId/{id}")
    PlayerDTO getPlayerByGoogleId(@PathVariable String id);

}
