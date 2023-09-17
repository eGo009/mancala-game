package com.mancala.processor;

import org.springframework.stereotype.Service;

import com.mancala.exception.UnexpectedGameActionException;
import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Player;

@Service
public class GameProcessor {

    /**
     * Method modifies the given game structure with the steps:
     * <ul>
     *     <li>1. Take stones from selected pit.</li>
     *     <li>2. Put stones one by one to the next pits(counterclockwise) except enemy store pit until stones are over.</li>
     *     <li>3. If the last placed stone goes to the empty pit - capture it and all the stones from opposite pit. Captured stones go the the player's store pit.</li>
     *     <li>4. If at least one player has ran out of stones - move game to finished state and capture all the left stones to the other player store pit.</li>
     *     <li>5. If  the last placed stone goes to the store pit - player get a free action, GameState remains the same. Otherwise - turn is switched to another player.</li>
     * </ul>
     *
     * @param gameContext       current game structure
     * @param selectedPitNumber pit selected to take stones from
     */
    public void makeAction(GameContext gameContext, int selectedPitNumber) throws UnexpectedGameActionException {
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

    private void captureStonesIfCan(GameContext gameContext, int lastStonePlacedPitNumber) throws UnexpectedGameActionException {
        Player currentPlayer = gameContext.getCurrentPlayer();
        if (!currentPlayer.isActivePit(lastStonePlacedPitNumber) || gameContext.getPits()[lastStonePlacedPitNumber] > 1) {
            return;
        }
        int oppositePitNumber = gameContext.getOppositePitNumber(lastStonePlacedPitNumber);
        captureStones(gameContext, oppositePitNumber, currentPlayer.getStorePitNumber());
        captureStones(gameContext, lastStonePlacedPitNumber, currentPlayer.getStorePitNumber());
    }

    private void captureStones(GameContext gameContext, int pitToCaptureFrom, int pitToCaptureTo) {
        int capturedStones = gameContext.getPits()[pitToCaptureFrom];
        gameContext.getPits()[pitToCaptureTo] += capturedStones;
        gameContext.getPits()[pitToCaptureFrom] = 0;
    }

    private boolean finishGameIfNeeded(GameContext gameContext) {
        int stonesLeftInPlayer1ActivePits = gameContext.calculateStonesLeftInActivePitsForPlayer(gameContext.getPlayer1());
        int stonesLeftInPlayer2ActivePits = gameContext.calculateStonesLeftInActivePitsForPlayer(gameContext.getPlayer2());
        boolean finished = false;
        if (stonesLeftInPlayer1ActivePits == 0) {
            gameContext.getPits()[gameContext.getPlayer2().getStorePitNumber()] += stonesLeftInPlayer2ActivePits;
            finished = true;
        } else if (stonesLeftInPlayer2ActivePits == 0) {
            gameContext.getPits()[gameContext.getPlayer1().getStorePitNumber()] += stonesLeftInPlayer1ActivePits;
            finished = true;
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

    private void switchTurnIfNeeded(GameContext gameContext, int lastStonePlacedPitNumber) throws UnexpectedGameActionException {
        Player currentPlayer = gameContext.getCurrentPlayer();
        if (currentPlayer.getStorePitNumber() == lastStonePlacedPitNumber) {
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
