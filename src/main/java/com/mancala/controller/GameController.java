package com.mancala.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.manager.GameManager;
import com.mancala.model.CreateGameRequest;
import com.mancala.model.GameActionRequest;
import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;

@RestController
public class GameController {

    @Autowired
    private GameContext gameContext;

    @Autowired
    private GameManager gameManager;

    @PostMapping("/start")
    public GameResponse createGame(@RequestBody CreateGameRequest createGameRequest) {
        //not implemented
        return null;
    }

    @GetMapping("/game")
    public GameResponse getCurrentGame() {
        //not implemented
        return null;
    }

    @PatchMapping("/action")
    public GameResponse makeAction(@RequestBody GameActionRequest gameActionRequest) {
        //not implemented
        return null;
    }

    @DeleteMapping("/reset")
    public GameResponse resetGame() {
        //not implemented
        return null;
    }

}
