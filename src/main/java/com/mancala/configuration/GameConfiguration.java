package com.mancala.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.mancala.GameConstants;
import com.mancala.model.GameContext;
import com.mancala.model.GameState;
import com.mancala.model.Player;

@Configuration
public class GameConfiguration {

    @Bean
    @SessionScope
    public GameContext gameContext() {
        int[] pits = new int[GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2];
        Arrays.fill(pits, 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1, GameConstants.STARTING_STONES_IN_PIT);
        Arrays.fill(pits, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, pits.length - 1, GameConstants.STARTING_STONES_IN_PIT);
        return new GameContext(
                new Player("Player1", 0, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER - 1),
                new Player("Player2", GameConstants.STARTING_TOTAL_PITS_PER_PLAYER, GameConstants.STARTING_TOTAL_PITS_PER_PLAYER * 2 - 1),
                GameState.PLAYER1_TURN,
                pits);
    }
}