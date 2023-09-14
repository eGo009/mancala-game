package com.mancala.model;

import java.util.Arrays;

import org.springframework.lang.Nullable;

import com.mancala.GameConstants;

import lombok.Data;

@Data
public class GameContext {

    private Player player1;
    private Player player2;
    @Nullable
    private String winnerName;
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

    @Nullable
    public Player getCurrentPlayer() {
        switch (getState()) {
            case PLAYER1_TURN:
                return getPlayer1();
            case PLAYER2_TURN:
                return getPlayer2();
            default:
                return null;
        }
    }

    @Nullable
    public Player getOppositePlayer() {
        switch (getState()) {
            case PLAYER1_TURN:
                return getPlayer2();
            case PLAYER2_TURN:
                return getPlayer1();
            default:
                return null;
        }
    }

    public boolean isStoneCanBePlacedToPit(int pitToPlaceStone) {
        return pitToPlaceStone != getEnemyStorePitNumber();
    }

    public int getEnemyStorePitNumber() {
        Player oppositePlayer = getOppositePlayer();
        if (oppositePlayer != null) {
            return oppositePlayer.getStorePitNumber();
        }
        return -1;
    }

    public int getOppositePitNumber(int pitNumber) {
        if (pitNumber == player2.getStorePitNumber()) {
            return pitNumber;
        }
        return pits.length - 2 - pitNumber;
    }

    public int calculateStonesLeftInActivePitsForPlayer(Player player) {
        int stonesLeft = 0;
        for (int i = player.getStartPitNumber(); i < player.getStorePitNumber(); i++) {
            stonesLeft += pits[i];
        }
        return stonesLeft;
    }

    public boolean isEmptyPit(int pitNumber) {
        return getPits()[pitNumber] <= 0;
    }
}
