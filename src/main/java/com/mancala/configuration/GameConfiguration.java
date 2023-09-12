package com.mancala.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.mancala.model.GameContext;

@Configuration
public class GameConfiguration {

    @Bean
    @SessionScope
    public GameContext gameContext() {
        GameContext gameContext = new GameContext();
        gameContext.setDefaultGameContext();
        return gameContext;
    }
}