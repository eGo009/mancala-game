package com.mancala.validator;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Player;

public class GameValidator {

    public static ValidationResult validateSelectedPitNumber(GameContext gameContext, int selectedPitNumber) {
        ValidationResult validationResult = validateNotPlayerActivePitSelected(gameContext, selectedPitNumber);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return validateEmptyPitSelected(gameContext, selectedPitNumber);
    }

    private static ValidationResult validateEmptyPitSelected(GameContext gameContext, int selectedPitNumber) {
        if (gameContext.isEmptyPit(selectedPitNumber)) {
            return new ValidationResult(false, String.format("No stones in pit %d, the action isn't possible.", selectedPitNumber));
        }
        return new ValidationResult(true, null);
    }

    private static ValidationResult validateNotPlayerActivePitSelected(GameContext gameContext, int selectedPitNumber) {
        if (gameContext.getState() == GameState.FINISHED) {
            return new ValidationResult(false, "Game is finished, the action can't be made.");
        }
        String errorMessage = null;
        Player player = gameContext.getCurrentPlayer();
        boolean success = player.isActivePit(selectedPitNumber);
        if (!success) {
            errorMessage = String.format("Player %s can't choose a pit number %d", player.getName(), selectedPitNumber);
        }
        return new ValidationResult(success, errorMessage);
    }
}
