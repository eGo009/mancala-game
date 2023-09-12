package com.mancala.model;

import java.util.Arrays;

import com.mancala.GameConstants;

public class GameContext {

    private Player player1;
    private Player player2;
    private GameState state;
    private int[] pits;

    public void setDefaultGameContext() {
        int[] pits = new int[GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2];
        Arrays.fill(pits, 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1, GameConstants.STARTING_STONES_IN_PIT);
        Arrays.fill(pits, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, pits.length - 1, GameConstants.STARTING_STONES_IN_PIT);
        setPits(pits);
        setPlayer1(new Player("Player1", 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1));
        setPlayer2(new Player("Player2", GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2 - 1));
        setState(GameState.PLAYER1_TURN);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }
}
