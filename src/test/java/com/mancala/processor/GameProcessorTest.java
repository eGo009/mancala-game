package com.mancala.processor;

import static com.mancala.CommonTestUtils.prepareDefaultGameContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;

public class GameProcessorTest {


    private GameProcessor gameProcessor = new GameProcessor();

    @Test
    public void makeActionShouldTakeStonesFromSelectedPitAndPlaceThemToNextPits() {
        GameContext gameContext = prepareDefaultGameContext();
        gameProcessor.makeAction(gameContext, 1);
        assertEquals(0, gameContext.getPits()[1]);
        assertEquals(7, gameContext.getPits()[2]);
        assertEquals(7, gameContext.getPits()[3]);
        assertEquals(7, gameContext.getPits()[4]);
        assertEquals(7, gameContext.getPits()[5]);
        assertEquals(1, gameContext.getPits()[6]);
        assertEquals(7, gameContext.getPits()[7]);
        assertEquals(6, gameContext.getPits()[8]);
        assertEquals(6, gameContext.getPits()[9]);
        assertEquals(6, gameContext.getPits()[10]);
        assertEquals(6, gameContext.getPits()[11]);
        assertEquals(6, gameContext.getPits()[12]);
        assertEquals(0, gameContext.getPits()[13]);
        assertEquals(6, gameContext.getPits()[0]);
    }

    @Test
    public void makeActionShouldSkipEnemyPitWhenPlacingStones() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.getPits()[5] = 8;
        gameProcessor.makeAction(gameContext, 5);
        assertEquals(0, gameContext.getPits()[5]);
        assertEquals(1, gameContext.getPits()[6]);
        assertEquals(7, gameContext.getPits()[7]);
        assertEquals(7, gameContext.getPits()[8]);
        assertEquals(7, gameContext.getPits()[9]);
        assertEquals(7, gameContext.getPits()[10]);
        assertEquals(7, gameContext.getPits()[11]);
        assertEquals(7, gameContext.getPits()[12]);
        assertEquals(0, gameContext.getPits()[13]);
        assertEquals(7, gameContext.getPits()[0]);
        assertEquals(6, gameContext.getPits()[1]);
        assertEquals(6, gameContext.getPits()[2]);
        assertEquals(6, gameContext.getPits()[3]);
        assertEquals(6, gameContext.getPits()[4]);
    }

    @Test
    public void makeActionShouldSwitchTurnToAnotherPlayer() {
        GameContext gameContext = prepareDefaultGameContext();
        gameProcessor.makeAction(gameContext, 2);
        assertEquals(GameState.PLAYER2_TURN, gameContext.getState());
    }

    @Test
    public void makeActionShouldNotSwitchTurnToAnotherPlayerWhenLastStonePlacedIntoPlayerStore() {
        GameContext gameContext = prepareDefaultGameContext();
        gameProcessor.makeAction(gameContext, 0);
        assertEquals(GameState.PLAYER1_TURN, gameContext.getState());
    }

    @Test
    public void makeActionShouldSwitchGameToFinishedWhenNoStonesLeftInActivePitsOfPlayer1() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.getPits()[0] = 0;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[2] = 0;
        gameContext.getPits()[3] = 0;
        gameContext.getPits()[4] = 0;
        gameContext.getPits()[5] = 1;
        gameProcessor.makeAction(gameContext, 5);
        assertEquals(GameState.FINISHED, gameContext.getState());
    }

    @Test
    public void makeActionShouldSwitchGameToFinishedWhenNoStonesLeftInActivePitsOfPlayer2() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.PLAYER2_TURN);
        gameContext.getPits()[7] = 0;
        gameContext.getPits()[8] = 0;
        gameContext.getPits()[9] = 0;
        gameContext.getPits()[10] = 0;
        gameContext.getPits()[11] = 0;
        gameContext.getPits()[12] = 1;
        gameProcessor.makeAction(gameContext, 12);
        assertEquals(GameState.FINISHED, gameContext.getState());
    }

    @Test
    public void makeActionShouldSetPlayer1AsWinnerWhenPlayer1StoreContainsMoreStonesAndGameGoingFinished() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.PLAYER2_TURN);
        gameContext.getPits()[0] = 0;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[2] = 0;
        gameContext.getPits()[3] = 0;
        gameContext.getPits()[4] = 0;
        gameContext.getPits()[5] = 0;
        gameContext.getPits()[6] = 100;
        gameContext.getPits()[7] = 0;
        gameContext.getPits()[8] = 0;
        gameContext.getPits()[9] = 0;
        gameContext.getPits()[10] = 0;
        gameContext.getPits()[11] = 0;
        gameContext.getPits()[12] = 1;
        gameContext.getPits()[13] = 0;
        gameProcessor.makeAction(gameContext, 12);
        assertEquals("Player1", gameContext.getWinnerName());
    }

    @Test
    public void makeActionShouldSetPlayer2AsWinnerWhenPlayer2StoreContainsMoreStonesAndGameGoingFinished() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.PLAYER2_TURN);
        gameContext.getPits()[0] = 0;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[2] = 0;
        gameContext.getPits()[3] = 0;
        gameContext.getPits()[4] = 0;
        gameContext.getPits()[5] = 0;
        gameContext.getPits()[6] = 0;
        gameContext.getPits()[7] = 0;
        gameContext.getPits()[8] = 0;
        gameContext.getPits()[9] = 0;
        gameContext.getPits()[10] = 0;
        gameContext.getPits()[11] = 0;
        gameContext.getPits()[12] = 1;
        gameContext.getPits()[13] = 100;
        gameProcessor.makeAction(gameContext, 12);
        assertEquals("Player2", gameContext.getWinnerName());
    }

    @Test
    public void makeActionShouldSetNoWinnersWhenPlayersHasSameStonesCountInStoreAndGameGoingFinished() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.PLAYER2_TURN);
        gameContext.getPits()[0] = 0;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[2] = 0;
        gameContext.getPits()[3] = 0;
        gameContext.getPits()[4] = 0;
        gameContext.getPits()[5] = 0;
        gameContext.getPits()[6] = 1;
        gameContext.getPits()[7] = 0;
        gameContext.getPits()[8] = 0;
        gameContext.getPits()[9] = 0;
        gameContext.getPits()[10] = 0;
        gameContext.getPits()[11] = 0;
        gameContext.getPits()[12] = 1;
        gameContext.getPits()[13] = 0;
        gameProcessor.makeAction(gameContext, 12);
        assertNull(gameContext.getWinnerName());
    }

    @Test
    public void makeActionShouldCollectAllLeftStonesOfPlayer1ToStoreWhenPlayer2HasNoStonesLeftInActivePits() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.PLAYER2_TURN);
        gameContext.getPits()[0] = 6;
        gameContext.getPits()[1] = 6;
        gameContext.getPits()[2] = 6;
        gameContext.getPits()[3] = 6;
        gameContext.getPits()[4] = 6;
        gameContext.getPits()[5] = 6;
        gameContext.getPits()[6] = 0;
        gameContext.getPits()[7] = 0;
        gameContext.getPits()[8] = 0;
        gameContext.getPits()[9] = 0;
        gameContext.getPits()[10] = 0;
        gameContext.getPits()[11] = 0;
        gameContext.getPits()[12] = 1;
        gameContext.getPits()[13] = 35;
        gameProcessor.makeAction(gameContext, 12);
        assertEquals(36, gameContext.getPits()[6]);
    }

    @Test
    public void makeActionShouldCollectAllLeftStonesOfPlayer2ToStoreWhenPlayer1HasNoStonesLeftInActivePits() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.getPits()[0] = 0;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[2] = 0;
        gameContext.getPits()[3] = 0;
        gameContext.getPits()[4] = 0;
        gameContext.getPits()[5] = 1;
        gameContext.getPits()[6] = 35;
        gameContext.getPits()[7] = 6;
        gameContext.getPits()[8] = 6;
        gameContext.getPits()[9] = 6;
        gameContext.getPits()[10] = 6;
        gameContext.getPits()[11] = 6;
        gameContext.getPits()[12] = 6;
        gameContext.getPits()[13] = 0;
        gameProcessor.makeAction(gameContext, 5);
        assertEquals(36, gameContext.getPits()[13]);
    }

    @Test
    public void makeActionShouldNotChangeGameContextWhenGameIsFinished() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.setState(GameState.FINISHED);
        GameContext expectedGameContext = prepareDefaultGameContext();
        expectedGameContext.setState(GameState.FINISHED);
        gameProcessor.makeAction(gameContext, 5);
        assertEquals(expectedGameContext, gameContext);
    }

    @Test
    public void makeActionShouldCaptureStoneFromOwnAndOppositePitWhenLastStonePlacedIntoEmptyPit() {
        GameContext gameContext = prepareDefaultGameContext();
        gameContext.getPits()[0] = 1;
        gameContext.getPits()[1] = 0;
        gameContext.getPits()[11] = 1;
        gameProcessor.makeAction(gameContext, 0);
        assertEquals(2, gameContext.getPits()[6]);
        assertEquals(0, gameContext.getPits()[1]);
        assertEquals(0, gameContext.getPits()[11]);
    }
}
