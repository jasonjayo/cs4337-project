package com.example.teamservice.service;

import com.example.teamservice.model.Team;
import com.example.teamservice.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    /**
     * update the values of an existing team
     *
     * @param id          ID of team to update
     * @param updatedTeam the updated team
     * @return the updated team
     */
    public Team updateTeam(Long id, Team updatedTeam) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent()) {
            Team existingTeam = teamOptional.get();
            existingTeam.setTeamName(updatedTeam.getTeamName());
            existingTeam.setManager(updatedTeam.getManager());
            existingTeam.setPin(updatedTeam.getPin());
            return teamRepository.save(existingTeam);
        } else {
            return null;
        }
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

}
