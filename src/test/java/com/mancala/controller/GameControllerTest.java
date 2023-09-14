package com.mancala.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mancala.model.CreateGameRequest;
import com.mancala.model.GameActionRequest;
import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.model.Player;
import com.mancala.processor.GameProcessor;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameContext gameContext;

    @Mock
    private GameProcessor gameProcessor;

    @InjectMocks
    private GameController gameController;

    private static Player PLAYER1_STUB = new Player("test1", 0, 6);
    private static Player PLAYER2_STUB = new Player("test2", 7, 13);

    @Test
    public void createGameShouldReturnGameResponseWithDefinedPlayerNamesFromRequest() {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.createGame(new CreateGameRequest("newName1", "newName2"));
        assertEquals("newName1", gameResponse.getPlayer1Name());
        assertEquals("newName2", gameResponse.getPlayer2Name());
        resetTestData();
    }

    @Test
    public void createGameShouldReturnGameResponseWithDefaultPlayerNamesWhenNamesFromRequestAreEmpty() {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.createGame(new CreateGameRequest(null, null));
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
    }

    @Test
    public void getCurrentGameShouldReturnGameResponseWithGameContextData() {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.getCurrentGame();
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
    }

    @Test
    public void makeActionShouldReturnErrorResponseAndNotInvokeGameProcessorWhenValidationFailed() {
        doReturn(PLAYER1_STUB).when(gameContext).getCurrentPlayer();
        GameResponse gameResponse = gameController.makeAction(new GameActionRequest(9));
        assertFalse(gameResponse.isSuccess());
        assertNotNull(gameResponse.getStatusMessage());
        verify(gameProcessor, never()).makeAction(gameContext, 9);
    }

    @Test
    public void makeActionShouldReturnSuccessResponseAndInvokeGameProcessorWhenValidationPassed() {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.makeAction(new GameActionRequest(1));
        assertTrue(gameResponse.isSuccess());
        assertNotNull(gameResponse.getStatusMessage());
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
        verify(gameProcessor).makeAction(gameContext, 1);
    }

    @Test
    public void resetGameShouldSetDefaultGameContextAndReturnGameInfo() {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.resetGame();
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
        verify(gameContext).setDefaultGameContext();
    }

    private void prepareGameContextStub() {
        doReturn(PLAYER1_STUB).when(gameContext).getPlayer1();
        doReturn(PLAYER2_STUB).when(gameContext).getPlayer2();
        doReturn(PLAYER1_STUB).when(gameContext).getCurrentPlayer();
        doReturn(new int[0]).when(gameContext).getPits();
    }

    private void resetTestData() {
        PLAYER1_STUB = new Player("test1", 0, 6);
        PLAYER2_STUB = new Player("test2", 7, 13);
    }
}
