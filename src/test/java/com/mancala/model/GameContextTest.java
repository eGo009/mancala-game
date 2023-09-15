package com.mancala.model;

import static com.mancala.CommonTestUtils.prepareDefaultGameContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;

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
    public void getCurrentPlayerShouldReturnPlayer1ForPlayer1TurnStatus() {
        assertEquals(gameContext.getPlayer1(), gameContext.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayerShouldReturnPlayer2ForPlayer2TurnStatus() {
        gameContext.setState(GameState.PLAYER2_TURN);
        assertEquals(gameContext.getPlayer2(), gameContext.getCurrentPlayer());
        gameContext.setState(GameState.PLAYER1_TURN);

    }

    @Test
    public void getCurrentPlayerShouldReturnNullForFinishedTurnStatus() {
        gameContext.setState(GameState.FINISHED);
        assertNull(gameContext.getCurrentPlayer());
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void getOppositePlayerShouldReturnPlayer2ForPlayer1TurnStatus() {
        assertEquals(gameContext.getPlayer2(), gameContext.getOppositePlayer());
    }

    @Test
    public void getOppositePlayerShouldReturnPlayer1ForPlayer2TurnStatus() {
        gameContext.setState(GameState.PLAYER2_TURN);
        assertEquals(gameContext.getPlayer1(), gameContext.getOppositePlayer());
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void getOppositePlayerShouldReturnNullForFinishedTurnStatus() {
        gameContext.setState(GameState.FINISHED);
        assertNull(gameContext.getOppositePlayer());
        gameContext.setState(GameState.PLAYER1_TURN);
    }

    @Test
    public void isStoneCanBePlacedToPitShouldReturnTrueWhenPitIsNotEnemyStore() {
        doReturn(7).when(gameContextSpy).getEnemyStorePitNumber();
        assertTrue(gameContextSpy.isStoneCanBePlacedToPit(13));
    }

    @Test
    public void isStoneCanBePlacedToPitShouldReturnFalseWhenPitIsEnemyStore() {
        doReturn(7).when(gameContextSpy).getEnemyStorePitNumber();
        assertFalse(gameContextSpy.isStoneCanBePlacedToPit(7));
    }

    @Test
    public void getEnemyStorePitNumberShouldReturnMinus1ForEmptyOppositePlayer() {
        doReturn(null).when(gameContextSpy).getOppositePlayer();
        assertEquals(-1, gameContextSpy.getEnemyStorePitNumber());
    }

    @Test
    public void getEnemyStorePitNumberShouldReturnOppositePlayerPitNumber() {
        doReturn(new Player(null, 0, 6)).when(gameContextSpy).getOppositePlayer();
        assertEquals(6, gameContextSpy.getEnemyStorePitNumber());
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
