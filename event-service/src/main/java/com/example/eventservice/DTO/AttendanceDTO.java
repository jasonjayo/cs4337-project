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

    // Getters and Setters for playerId, playerName, and status
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
