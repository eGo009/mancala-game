package com.mancala.configuration;

import static org.junit.Assert.assertEquals;
import static com.mancala.CommonTestUtils.prepareDefaultGameContext;

import org.junit.jupiter.api.Test;

public class GameConfigurationTest {

    private GameConfiguration gameConfiguration = new GameConfiguration();

    @Test
    public void gameContext() {
        assertEquals(prepareDefaultGameContext(), gameConfiguration.gameContext());
    }
}
