package com.example.shared.dto;

/**
 * Data transfer object for Team. This allows for effective server-to-server between the team service
 * and other services.
 */
public class TeamDTO {
    private Long teamId;
    private String pin;
    private String teamName;
    private String manager;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String name, String manager) {
        this.teamId = id;
        this.teamName = name;
        this.manager = manager;
    }

    public String getPin() {
        return pin;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getManager() {
        return manager;
    }

}
