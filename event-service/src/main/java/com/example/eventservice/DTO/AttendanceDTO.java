package com.example.eventservice.DTO;

public class AttendanceDTO {
    private Long playerId;
    private String playerName;
    private String status;

    // Constructor with all three fields
    public AttendanceDTO(Long playerId, String playerName, String status) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.status = status;
    }

    // TODO: Getters and Setters
}
