package com.mancala.converter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.model.GameState;
import com.mancala.model.Pit;
import com.mancala.validator.ValidationResult;

public class GameEntitiesConverterTest {

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnErrorGameResponseForFailedValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), prepareFailedValidationResult());
        Assert.assertFalse(gameResponse.isSuccess());
        Assert.assertEquals("failure message", gameResponse.getStatusMessage());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnSuccessGameResponseForSuccessValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), prepareSuccessValidationResult());
        Assert.assertTrue(gameResponse.isSuccess());
        Assert.assertEquals("Player1 turn", gameResponse.getStatusMessage());
        Assert.assertArrayEquals(prepareDefaultPits(), gameResponse.getPits());
        Assert.assertEquals("Player1", gameResponse.getPlayer1Name());
        Assert.assertEquals("Player2", gameResponse.getPlayer2Name());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnSuccessGameResponseForNullValidationResult() {
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(prepareGameContext(), null);
        Assert.assertTrue(gameResponse.isSuccess());
        Assert.assertEquals("Player1 turn", gameResponse.getStatusMessage());
        Assert.assertArrayEquals(prepareDefaultPits(), gameResponse.getPits());
        Assert.assertEquals("Player1", gameResponse.getPlayer1Name());
        Assert.assertEquals("Player2", gameResponse.getPlayer2Name());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnDrawStatusMessageWhenGameIsFinishedWithoutWinners() {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext, null);
        Assert.assertEquals("Draw", gameResponse.getStatusMessage());
    }

    @Test
    public void convertInternalGameStructureIntoGameResponseShouldReturnWinnerStatusMessageWhenGameIsFinishedWithWinner() {
        GameContext gameContext = prepareGameContext();
        gameContext.setState(GameState.FINISHED);
        gameContext.setWinnerName("Player2");
        GameResponse gameResponse = GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext, null);
        Assert.assertEquals("Player2 is winner", gameResponse.getStatusMessage());
    }

    private Pit[] prepareDefaultPits() {
        return new Pit[]{new Pit(6, true),
                new Pit(6, true),
                new Pit(6, true),
                new Pit(6, true),
                new Pit(6, true),
                new Pit(6, true),
                new Pit(0, false),
                new Pit(6, false),
                new Pit(6, false),
                new Pit(6, false),
                new Pit(6, false),
                new Pit(6, false),
                new Pit(6, false),
                new Pit(0, false)
        };
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
