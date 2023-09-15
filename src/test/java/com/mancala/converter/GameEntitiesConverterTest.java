package com.mancala.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.mancala.CommonTestUtils.prepareDefaultPits;
import org.junit.jupiter.api.Test;

import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.model.GameState;
import com.mancala.validator.ValidationResult;

public class GameEntitiesConverterTest {

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnErrorGameResponseForFailedValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), prepareFailedValidationResult());
        assertFalse(gameResponse.isSuccess());
        assertEquals("failure message", gameResponse.getStatusMessage());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnSuccessGameResponseForSuccessValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), prepareSuccessValidationResult());
        assertTrue(gameResponse.isSuccess());
        assertEquals("Player1 turn", gameResponse.getStatusMessage());
        assertArrayEquals(prepareDefaultPits(), gameResponse.getPits());
        assertEquals("Player1", gameResponse.getPlayer1Name());
        assertEquals("Player2", gameResponse.getPlayer2Name());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnSuccessGameResponseForNullValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), null);
        assertTrue(gameResponse.isSuccess());
        assertEquals("Player1 turn", gameResponse.getStatusMessage());
        assertArrayEquals(prepareDefaultPits(), gameResponse.getPits());
        assertEquals("Player1", gameResponse.getPlayer1Name());
        assertEquals("Player2", gameResponse.getPlayer2Name());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnDrawStatusMessageWhenGameIsFinishedWithoutWinners() {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext, null);
        assertEquals("Draw", gameResponse.getStatusMessage());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnWinnerStatusMessageWhenGameIsFinishedWithWinner() {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        gameContext.setWinnerName("Player2");
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext, null);
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

    private ValidationResult prepareSuccessValidationResult() {
        return new ValidationResult(true, null);
    }

}
