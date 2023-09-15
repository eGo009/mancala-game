package com.mancala.validator;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Player;

public class GameValidator {

    /**
     * Validate selectedPitNumber with the rules:
     * <ul>
     *     <li>1. Selected pit should belong to the current player and shouldn't be a store pit.</li>
     *     <li>2. Selected pit shouldn't be empty.</li>
     * </ul>
     *
     * @param gameContext       given game structure
     * @param selectedPitNumber selected pit number to be validated
     * @return error validation result (success = false, errorMessage is defined) if validation rules aren't matched, otherwise - success validation result (success = true,
     * errorMessage is null).
     */
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
