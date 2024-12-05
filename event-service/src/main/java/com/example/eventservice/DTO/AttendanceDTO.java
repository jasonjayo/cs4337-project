package com.example.eventservice.DTO;

/**
 * Data Transfer Object (DTO) for attendance information.
 * This class encapsulates data about a player's attendance for a specific event.
 */
public class AttendanceDTO {
    private Long playerId;
    private String playerName;
    private String status;

    /**
     * Constructor to initialize all fields of the AttendanceDTO.
     * @param playerId The unique ID of the player.
     * @param playerName The name of the player.
     * @param status The attendance status of the player.
     */
    public AttendanceDTO(Long playerId, String playerName, String status) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.status = status;
    }

    /**
     * Retrieves the player's ID.
     * @return The player's ID.
     */
    public Long getPlayerId() { return playerId; }

    /**
     * Sets the player's ID.
     * @param playerId The player's ID to set.
     */
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    /**
     * Retrieves the player's name.
     * @return The player's name.
     */
    public String getPlayerName() { return playerName; }

    /**
     * Sets the player's name.
     * @param playerName The player's name to set.
     */
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    /**
     * Retrieves the attendance status of the player.
     * @return The player's attendance status.
     */
    public String getStatus() { return status; }

    /**
     * Sets the attendance status of the player.
     * @param status The attendance status to set (e.g., "true" or "false").
     */
    public void setStatus(String status) { this.status = status; }
}
