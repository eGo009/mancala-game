package com.mancala.validator;

import com.mancala.model.GameContext;
import com.mancala.model.Player;

public class GameValidator {
    public static ValidationResult validateSelectedPitNumber(GameContext gameContext, int selectedPitNumber) {
        ValidationResult validationResult = validateEnemyPitOrStorePitSelected(gameContext, selectedPitNumber);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return validateEmptyPitSelected(gameContext, selectedPitNumber);
    }

    private static ValidationResult validateEmptyPitSelected(GameContext gameContext, int selectedPitNumber) {
        int stonesToMove = gameContext.getPits()[selectedPitNumber];
        if (stonesToMove <= 0) {
            return new ValidationResult(false, String.format("No stones in pit %d, action isn't possible.", selectedPitNumber));
        }
        return new ValidationResult(true, null);
    }

    private static ValidationResult validateEnemyPitOrStorePitSelected(GameContext gameContext, int selectedPitNumber) {
        boolean success;
        String errorMessage = null;
        Player player = null;
        switch (gameContext.getState()) {
            case PLAYER1_TURN:
                player = gameContext.getPlayer1();
                break;
            case PLAYER2_TURN:
                player = gameContext.getPlayer2();
                break;
            case FINISHED:
                return new ValidationResult(false, "Game is finished, action can't be made.");
        }
        success = player.getStartPitNumber() <= selectedPitNumber && gameContext.getPlayer1().getStorePitNumber() > selectedPitNumber;
        if (!success) {
            errorMessage = String.format("Player %s can't choose pit number %d", player.getName(), selectedPitNumber);
        }
        return new ValidationResult(success, errorMessage);
    }
}
