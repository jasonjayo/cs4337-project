package com.example.shared.dto;

/**
 * Data transfer object for Player. This allows for effective server-to-server between the player service
 * and other services.
 */
public class PlayerDTO {


    public int playerId;
    public String playerName;
    public String googleId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
