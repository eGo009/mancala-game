package com.mancala.manager;

import org.springframework.stereotype.Service;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Player;

@Service
public class GameProcessor {

    public void makeAction(GameContext gameContext, int selectedPitNumber) {
        int stonesToMove = gameContext.getPits()[selectedPitNumber];
        gameContext.getPits()[selectedPitNumber] = 0;
        int pitToPlaceStone = selectedPitNumber;
        while (stonesToMove > 0) {
            pitToPlaceStone++;
            if (!gameContext.isStoneCanBePlacedToPit(pitToPlaceStone)) {
                pitToPlaceStone++;
            }
            if (pitToPlaceStone >= gameContext.getPits().length) {
                pitToPlaceStone = 0;
            }
            gameContext.getPits()[pitToPlaceStone]++;
            stonesToMove--;
        }
        captureStonesIfCan(gameContext, pitToPlaceStone);
        if (!finishGameIfNeeded(gameContext)) {
            switchTurnIfNeeded(gameContext, pitToPlaceStone);
        }
    }

    private void captureStonesIfCan(GameContext gameContext, int lastStonePlacedPitNumber) {
        Player currentPlayer = gameContext.getCurrentPlayer();
        if (currentPlayer == null || !currentPlayer.isActivePit(lastStonePlacedPitNumber)) {
            return;
        }
        int oppositePitNumber = gameContext.getOppositePitNumber(lastStonePlacedPitNumber);
        int capturedStones = gameContext.getPits()[oppositePitNumber];
        gameContext.getPits()[currentPlayer.getStorePitNumber()] += capturedStones;
        gameContext.getPits()[oppositePitNumber] = 0;
    }

    private boolean finishGameIfNeeded(GameContext gameContext) {
        int stonesLeftInPlayer1ActivePits = gameContext.calculateStonesLeftInActivePitsForPlayer(gameContext.getPlayer1());
        int stonesLeftInPlayer2ActivePits = gameContext.calculateStonesLeftInActivePitsForPlayer(gameContext.getPlayer2());
        boolean finished = false;
        if (stonesLeftInPlayer1ActivePits == 0) {
            gameContext.getPits()[gameContext.getPlayer2().getStorePitNumber()] = stonesLeftInPlayer2ActivePits;
            finished = true;
        } else if (stonesLeftInPlayer2ActivePits == 0) {
            gameContext.getPits()[gameContext.getPlayer2().getStorePitNumber()] = stonesLeftInPlayer1ActivePits;
        }
        if (finished) {
            gameContext.setState(GameState.FINISHED);
            determineWinner(gameContext);
        }
        return finished;
    }

    private void determineWinner(GameContext gameContext) {
        int player1Score = gameContext.getPits()[gameContext.getPlayer1().getStorePitNumber()];
        int player2Score = gameContext.getPits()[gameContext.getPlayer2().getStorePitNumber()];
        if (player1Score > player2Score) {
            gameContext.setWinnerName(gameContext.getPlayer1().getName());
        } else if (player2Score > player1Score) {
            gameContext.setWinnerName(gameContext.getPlayer2().getName());
        }
    }

    private void switchTurnIfNeeded(GameContext gameContext, int lastStonePlacedPitNumber) {
        Player currentPlayer = gameContext.getCurrentPlayer();
        if (currentPlayer == null || currentPlayer.getStorePitNumber() == lastStonePlacedPitNumber) {
            return;
        }
        switchTurn(gameContext);
    }

    private void switchTurn(GameContext gameContext) {
        switch (gameContext.getState()) {
            case PLAYER1_TURN:
                gameContext.setState(GameState.PLAYER2_TURN);
                break;
            case PLAYER2_TURN:
                gameContext.setState(GameState.PLAYER1_TURN);
        }
    }
}
