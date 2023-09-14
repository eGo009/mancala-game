package com.mancala.validator;

import static com.mancala.CommonTestUtils.prepareDefaultGameContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;

public class GameValidatorTest {

    @Test
    public void validateSelectedPitNumberShouldReturnErrorWhenGameIsFinished() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.FINISHED);
        ValidationResult validationResult = GameValidator.validateSelectedPitNumber(gameContext, 0);
        assertEquals("Game is finished, action can't be made.", validationResult.getErrorMessage());
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void validateSelectedPitNumberShouldReturnErrorWhenSelectedPitNumberIsNotAvailableForPlayer() {
        GameContext gameContext = prepareDefaultGameContext();
        ValidationResult validationResult = GameValidator.validateSelectedPitNumber(gameContext, 10);
        assertEquals("Player Player1 can't choose pit number 10", validationResult.getErrorMessage());
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void validateSelectedPitNumberShouldReturnErrorWhenSelectedPitIsEmpty() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.getPits()[1] = 0;
        ValidationResult validationResult = GameValidator.validateSelectedPitNumber(gameContext, 1);
        assertEquals("No stones in pit 1, action isn't possible.", validationResult.getErrorMessage());
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void validateSelectedPitNumberShouldReturnSuccessAndNoErrorMessageForSuccessCase() {
        GameContext gameContext = prepareDefaultGameContext();
        ValidationResult validationResult = GameValidator.validateSelectedPitNumber(gameContext, 1);
        assertNull(validationResult.getErrorMessage());
        assertTrue(validationResult.isSuccess());
    }

}
