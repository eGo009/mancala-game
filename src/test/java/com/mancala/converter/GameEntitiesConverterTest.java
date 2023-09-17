package com.mancala.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.mancala.CommonTestUtils.prepareDefaultPits;

import org.junit.jupiter.api.Test;

import com.mancala.exception.UnexpectedGameActionException;
import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.model.GameState;
import com.mancala.validator.ValidationResult;

public class GameEntitiesConverterTest {

    @Test
    public void prepareErrorGameResponse() {
        GameResponse gameResponse = GameEntitiesConverter.prepareErrorGameResponse(prepareFailedValidationResult());
        assertFalse(gameResponse.isSuccess());
        assertEquals("failure message", gameResponse.getStatusMessage());
    }

    @Test
    public void prepareSuccessGameResponse() throws UnexpectedGameActionException {
        GameResponse gameResponse = GameEntitiesConverter.prepareSuccessGameResponse(prepareGameContext());
        assertTrue(gameResponse.isSuccess());
        assertEquals("Player1 turn", gameResponse.getStatusMessage());
        assertArrayEquals(prepareDefaultPits(), gameResponse.getPits());
        assertEquals("Player1", gameResponse.getPlayer1Name());
        assertEquals("Player2", gameResponse.getPlayer2Name());
    }

    @Test
    public void prepareSuccessGameResponseShouldReturnDrawStatusMessageWhenGameIsFinishedWithoutWinners() throws UnexpectedGameActionException {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        GameResponse gameResponse = GameEntitiesConverter.prepareSuccessGameResponse(gameContext);
        assertEquals("Draw", gameResponse.getStatusMessage());
    }

    @Test
    public void prepareSuccessGameResponseShouldReturnWinnerStatusMessageWhenGameIsFinishedWithWinner() throws UnexpectedGameActionException {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        gameContext.setWinnerName("Player2");
        GameResponse gameResponse = GameEntitiesConverter.prepareSuccessGameResponse(gameContext);
        assertEquals("Player2 is winner", gameResponse.getStatusMessage());
    }

    private GameContext prepareGameContext() {
        GameContext gameContext = new GameContext();
        gameContext.setDefaultGameContext();
        return gameContext;
    }

    private ValidationResult prepareFailedValidationResult() {
        return new ValidationResult(false, "failure message");
    }
}
