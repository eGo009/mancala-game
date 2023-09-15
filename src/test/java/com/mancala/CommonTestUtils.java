package com.mancala;

import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Pit;
import com.mancala.model.Player;

public class CommonTestUtils {


    public static GameContext prepareDefaultGameContext() {
        return new GameContext(
                new Player("Player1", 0, 6),
                new Player("Player2", 7, 13),
                null,
                GameState.PLAYER1_TURN,
                new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0}
        );
    }

    public static Pit[] prepareDefaultPits() {
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
}
