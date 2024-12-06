package com.example.shared.feign;

import com.example.shared.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client. This allows services that need to get data from the team service to do so.
 */
@FeignClient(name = "team-service")
public interface TeamClient {

    /**
     * This allows other services to use the getTeamById method provided by the team service.
     * Any service using this client can call getTeamById, and behind the scenes this client will connect to the
     * team service and call its getTeamById. These connections are handled for us thanks to the use of this
     * Feign client. Behind the scenes, we're making use of the player REST API.
     *
     * @param id ID of team to be fetched
     * @return a team DTO representing the team, if team with given ID found
     */
    @GetMapping("/api/teams/{id}")
    TeamDTO getTeamById(@PathVariable Long id);
}
