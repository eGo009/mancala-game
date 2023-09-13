package com.mancala.model;

import org.springframework.lang.Nullable;

public class GameResponse {

    @Nullable
    private String player1Name;
    @Nullable
    private String player2Name;

    private boolean success;
    private String statusMessage;

    @Nullable
    private Pit[] pits;

    public GameResponse(String player1Name, String player2Name, boolean success, String statusMessage, Pit[] pits) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.success = success;
        this.statusMessage = statusMessage;
        this.pits = pits;
    }

    @Nullable
    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(@Nullable String player1Name) {
        this.player1Name = player1Name;
    }

    @Nullable
    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(@Nullable String player2Name) {
        this.player2Name = player2Name;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Nullable
    public Pit[] getPits() {
        return pits;
    }

    public void setPits(@Nullable Pit[] pits) {
        this.pits = pits;
    }
}
