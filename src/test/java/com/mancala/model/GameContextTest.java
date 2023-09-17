package com.mancala.model;

import static com.mancala.CommonTestUtils.prepareDefaultGameContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;

import com.mancala.exception.UnexpectedGameActionException;

public class GameContextTest {


    private GameContext gameContext = prepareDefaultGameContext();
    private GameContext gameContextSpy = spy(gameContext);

    @Test
    public void setDefaultGameContext() {
        GameContext testGameContext = new GameContext(
                new Player("changedName1", 0, 6),
                new Player("changedName2", 7, 13),
                "changedName1",
                GameState.FINISHED,
                new int[]{0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 35}
        );

        testGameContext.setDefaultGameContext();
        assertEquals(gameContext, testGameContext);
    }

    @Test
    public void getCurrentPlayerShouldReturnPlayer1ForPlayer1TurnStatus() throws UnexpectedGameActionException {
        assertEquals(gameContext.getPlayer1(), gameContext.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayerShouldReturnPlayer2ForPlayer2TurnStatus() throws UnexpectedGameActionException {
        gameContext.setState(GameState.PLAYER2_TURN);
        assertEquals(gameContext.getPlayer2(), gameContext.getCurrentPlayer());
        gameContext.setState(GameState.PLAYER1_TURN);

    }

    @Test
    public void getCurrentPlayerShouldThrowExceptionForFinishedTurnStatus() {
        gameContext.setState(GameState.FINISHED);
        assertThrows(UnexpectedGameActionException.class, () -> {gameContext.getCurrentPlayer();});
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void getOppositePlayerShouldReturnPlayer2ForPlayer1TurnStatus() throws UnexpectedGameActionException {
        assertEquals(gameContext.getPlayer2(), gameContext.getOppositePlayer());
    }

    @Test
    public void getOppositePlayerShouldReturnPlayer1ForPlayer2TurnStatus() throws UnexpectedGameActionException {
        gameContext.setState(GameState.PLAYER2_TURN);
        assertEquals(gameContext.getPlayer1(), gameContext.getOppositePlayer());
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void getOppositePlayerShouldThrowExceptionForFinishedTurnStatus() {
        gameContext.setState(GameState.FINISHED);
        assertThrows(UnexpectedGameActionException.class, () -> {gameContext.getOppositePlayer();});
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void isStoneCanBePlacedToPitShouldReturnTrueWhenPitIsNotEnemyStore() throws UnexpectedGameActionException {
        doReturn(new Player(null, 0, 7)).when(gameContextSpy).getOppositePlayer();
        assertTrue(gameContextSpy.isStoneCanBePlacedToPit(13));
    }

    @Test
    public void isStoneCanBePlacedToPitShouldReturnFalseWhenPitIsEnemyStore() throws UnexpectedGameActionException {
        doReturn(new Player(null, 0, 7)).when(gameContextSpy).getOppositePlayer();
        assertFalse(gameContextSpy.isStoneCanBePlacedToPit(7));
    }

    @Test
    public void getOppositePitNumberShouldReturnSameStoreNumberForInputStoreNumber() {
        assertEquals(13, gameContext.getOppositePitNumber(13));
    }

    @Test
    public void getOppositePitNumberShouldReturnOppositeNumberForInputNotStoreNumber() {
        assertEquals(4, gameContext.getOppositePitNumber(8));
    }

    @Test
    public void calculateStonesLeftInActivePitsForPlayer() {
        assertEquals(36, gameContext.calculateStonesLeftInActivePitsForPlayer(gameContext.getPlayer1()));
    }

    @Test
    public void isEmptyPitShouldReturnTrueForZeroPitArrayElementWithSpecifiedNumber() {
        assertTrue(gameContext.isEmptyPit(6));
    }

    @Test
    public void isEmptyPitShouldReturnFalseForNotZeroPitArrayElementWithSpecifiedNumber() {
        assertFalse(gameContext.isEmptyPit(0));
    }
}
