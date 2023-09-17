package com.mancala.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mancala.exception.UnexpectedGameActionException;
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
    public void createGameShouldReturnGameResponseWithDefinedPlayerNamesFromRequest() throws UnexpectedGameActionException {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.createGame(new CreateGameRequest("newName1", "newName2"));
        assertEquals("newName1", gameResponse.getPlayer1Name());
        assertEquals("newName2", gameResponse.getPlayer2Name());
        resetTestData();
    }

    @Test
    public void createGameShouldReturnGameResponseWithDefaultPlayerNamesWhenNamesFromRequestAreEmpty() throws UnexpectedGameActionException {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.createGame(new CreateGameRequest(null, null));
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
    }

    @Test
    public void createGameShouldReturnErrorResponseWhenExceptionIsThrown() throws UnexpectedGameActionException {
        prepareFailedGameContextStub();
        GameResponse gameResponse = gameController.createGame(new CreateGameRequest(null, null));
        assertEquals("Unexpected game action: exception message", gameResponse.getStatusMessage());
        assertFalse(gameResponse.isSuccess());
    }

    @Test
    public void getCurrentGameShouldReturnGameResponseWithGameContextData() throws UnexpectedGameActionException {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.getCurrentGame();
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
    }

    @Test
    public void getCurrentGameShouldReturnErrorResponseWhenExceptionIsThrown() throws UnexpectedGameActionException {
        prepareFailedGameContextStub();
        GameResponse gameResponse = gameController.getCurrentGame();
        assertEquals("Unexpected game action: exception message", gameResponse.getStatusMessage());
        assertFalse(gameResponse.isSuccess());
    }

    @Test
    public void makeActionShouldReturnErrorResponseAndNotInvokeGameProcessorWhenValidationFailed() throws UnexpectedGameActionException {
        doReturn(PLAYER1_STUB).when(gameContext).getCurrentPlayer();
        GameResponse gameResponse = gameController.makeAction(new GameActionRequest(9));
        assertFalse(gameResponse.isSuccess());
        assertEquals("Player test1 can't choose a pit number 9", gameResponse.getStatusMessage());
        verify(gameProcessor, never()).makeAction(gameContext, 9);
    }

    @Test
    public void makeActionShouldReturnErrorResponseWhenExceptionIsThrown() throws UnexpectedGameActionException {
        doThrow(new UnexpectedGameActionException("exception message")).when(gameContext).getCurrentPlayer();
        GameResponse gameResponse = gameController.makeAction(new GameActionRequest(9));
        assertFalse(gameResponse.isSuccess());
        assertEquals("Unexpected game action: exception message", gameResponse.getStatusMessage());
    }

    @Test
    public void makeActionShouldReturnSuccessResponseAndInvokeGameProcessorWhenValidationPassed() throws UnexpectedGameActionException {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.makeAction(new GameActionRequest(1));
        assertTrue(gameResponse.isSuccess());
        assertNotNull(gameResponse.getStatusMessage());
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
        verify(gameProcessor).makeAction(gameContext, 1);
    }

    @Test
    public void resetGameShouldSetDefaultGameContextAndReturnGameInfo() throws UnexpectedGameActionException {
        prepareGameContextStub();
        GameResponse gameResponse = gameController.resetGame();
        assertEquals("test1", gameResponse.getPlayer1Name());
        assertEquals("test2", gameResponse.getPlayer2Name());
        verify(gameContext).setDefaultGameContext();
    }

    @Test
    public void resetGameShouldReturnErrorResponseWhenExceptionIsThrown() throws UnexpectedGameActionException {
        prepareFailedGameContextStub();
        GameResponse gameResponse = gameController.resetGame();
        assertEquals("Unexpected game action: exception message", gameResponse.getStatusMessage());
        assertFalse(gameResponse.isSuccess());
    }

    private void prepareGameContextStub() throws UnexpectedGameActionException {
        doReturn(PLAYER1_STUB).when(gameContext).getPlayer1();
        doReturn(PLAYER2_STUB).when(gameContext).getPlayer2();
        doReturn(PLAYER1_STUB).when(gameContext).getCurrentPlayer();
        doReturn(new int[0]).when(gameContext).getPits();
    }

    private void prepareFailedGameContextStub() throws UnexpectedGameActionException {
        doReturn(PLAYER1_STUB).when(gameContext).getPlayer1();
        doReturn(PLAYER2_STUB).when(gameContext).getPlayer2();
        doThrow(new UnexpectedGameActionException("exception message")).when(gameContext).getCurrentPlayer();
    }

    private void resetTestData() {
        PLAYER1_STUB = new Player("test1", 0, 6);
        PLAYER2_STUB = new Player("test2", 7, 13);
    }
}
