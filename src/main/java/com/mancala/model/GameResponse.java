package com.mancala.model;

public class GameResponse {

    private String player1Name;
    private String player2Name;

    private String statusMessage;

    private Pit[] pits;

    public GameResponse(String player1Name, String player2Name, String statusMessage, Pit[] pits) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.statusMessage = statusMessage;
        this.pits = pits;
    }
}
