package com.mancala.model;

public class GameContext {

    private Player player1;
    private Player player2;
    private GameState state;
    private int[] pits;

    public GameContext(Player player1, Player player2, GameState state, int[] pits) {
        this.player1 = player1;
        this.player2 = player2;
        this.state = state;
        this.pits = pits;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public GameState getState() {
        return state;
    }

    public int[] getPits() {
        return pits;
    }
}
