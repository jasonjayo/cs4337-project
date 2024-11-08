package com.example.playerservice.client;

import com.example.playerservice.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "team-service")
public interface TeamClient {
    @GetMapping("/api/teams")
    List<TeamDTO> getAllTeams();
}
