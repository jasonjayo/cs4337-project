package com.example.shared.feign;

import com.example.shared.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "team-service")
public interface TeamClient {
    @GetMapping("/api/teams/{id}")
    TeamDTO getTeamById(@PathVariable Long id);
}
