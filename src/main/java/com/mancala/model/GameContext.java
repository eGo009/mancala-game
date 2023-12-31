package com.mancala.model;

import java.util.Arrays;

import org.springframework.lang.Nullable;

import com.mancala.GameConstants;
import com.mancala.exception.UnexpectedGameActionException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameContext {

    private Player player1;
    private Player player2;
    @Nullable
    private String winnerName;
    private GameState state;
    private int[] pits;

    public void setDefaultGameContext() {
        int[] pits = new int[GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2];
        Arrays.fill(pits, 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1, GameConstants.STARTING_STONES_IN_PIT);
        Arrays.fill(pits, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, pits.length - 1, GameConstants.STARTING_STONES_IN_PIT);
        setPits(pits);
        setPlayer1(new Player("Player1", 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1));
        setPlayer2(new Player("Player2", GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2 - 1));
        setState(GameState.PLAYER1_TURN);
        setWinnerName(null);
    }

    /**
     * Get a current player based on the game state. Finished game has no current player, so calling the method for this case will result UnexpectedGameActionException
     *
     * @return current player
     * @throws UnexpectedGameActionException if game has no current player to return
     */
    public Player getCurrentPlayer() throws UnexpectedGameActionException {
        switch (getState()) {
            case PLAYER1_TURN:
                return getPlayer1();
            case PLAYER2_TURN:
                return getPlayer2();
            default:
                throw new UnexpectedGameActionException(String.format("Can't determine a current user for %s game state", getState().toString()));
        }
    }

    /**
     * Get an opposite player based on the game state. Finished game has no opposite player, so calling the method for this case will result UnexpectedGameActionException
     *
     * @return opposite player
     * @throws UnexpectedGameActionException if game has no opposite player to return
     */
    public Player getOppositePlayer() throws UnexpectedGameActionException {
        switch (getState()) {
            case PLAYER1_TURN:
                return getPlayer2();
            case PLAYER2_TURN:
                return getPlayer1();
            default:
                throw new UnexpectedGameActionException(String.format("Can't determine an opposite user for %s game state", getState().toString()));
        }
    }

    /**
     * Stones could be placed into the any pit except the enemy store pit.
     *
     * @param pitToPlaceStone pit to be checked
     * @return true if the pit is available for placing a stone
     */
    public boolean isStoneCanBePlacedToPit(int pitToPlaceStone) throws UnexpectedGameActionException {
        return pitToPlaceStone != getOppositePlayer().getStorePitNumber();
    }


    /**
     * Get a opposite pit number for the selected pit number. Pits o the game fields is arranged in the following order (numbers are the indexes of the pits in the GameContext):
     * <table>
     *     <tr>
     *         <td></td>   <td>12</td> <td>11</td> <td>10</td> <td>9</td> <td>8</td> <td>7</td>   <td></td>
     *     </tr>
     *     <tr>
     *         <td>13</td> <td></td>    <td></td>   <td></td>  <td></td>  <td></td>   <td></td>   <td>6</td>
     *     </tr>
     *     <tr>
     *         <td></td>  <td>0</td>    <td>1</td>  <td>2</td> <td>3</td> <td>4</td> <td>5</td>   <td></td>
     *     </tr>
     * </table>
     * So, there are several opposite pairs: 0-12, 1-11, 2-10 and so on.
     * Store pits don't have opposite pit, so method will return the same number for them.
     *
     * Every pair has 12 in total if we sum up the indexes: 0+12, 1+11, 2+10 etc...
     * So sum of pair is always (pitsArraySize - 2)  regardless of total pits count.
     *
     * @param pitNumber selected pit number
     * @return same value as input pitNumber for store pits and opposite pit number for other pits.
     */
    public int getOppositePitNumber(int pitNumber) {
        if (pitNumber == player2.getStorePitNumber()) {
            return pitNumber;
        }
        return pits.length - 2 - pitNumber;
    }

    /**
     * Calculate all stones in player pits except the store pit.
     *
     * @param player target player
     * @return count of calculated stones
     */
    public int calculateStonesLeftInActivePitsForPlayer(Player player) {
        int stonesLeft = 0;
        for (int i = player.getStartPitNumber(); i < player.getStorePitNumber(); i++) {
            stonesLeft += pits[i];
        }
        return stonesLeft;
    }

    public boolean isEmptyPit(int pitNumber) {
        return getPits()[pitNumber] <= 0;
    }
}
