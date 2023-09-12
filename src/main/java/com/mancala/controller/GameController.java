package com.mancala.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.converter.GameEntitiesConverter;
import com.mancala.manager.GameManager;
import com.mancala.model.CreateGameRequest;
import com.mancala.model.GameActionRequest;
import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.validator.GameValidator;
import com.mancala.validator.ValidationResult;

@RestController
public class GameController {

    @Autowired
    private GameContext gameContext;

    @Autowired
    private GameManager gameManager;

    @PostMapping("/start")
    public GameResponse createGame(@RequestBody CreateGameRequest createGameRequest) {
        gameContext.getPlayer1().setNameIfNotNull(createGameRequest.getPlayer1Name());
        gameContext.getPlayer2().setNameIfNotNull(createGameRequest.getPlayer2Name());
        return GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext);
    }

    @GetMapping("/game")
    public GameResponse getCurrentGame() {
        return GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext);
    }

    @PatchMapping("/action")
    public GameResponse makeAction(@RequestBody GameActionRequest gameActionRequest) {
        ValidationResult validationResult = GameValidator.validateSelectedPitNumber(gameContext, gameActionRequest.getSelectedPitNumber());
        if (validationResult.isSuccess()) {
            gameManager.makeAction(gameContext, gameActionRequest.getSelectedPitNumber());
        }
        return GameEntitiesConverter.convertInternalGameStructureIntoGameResponse(gameContext);
    }

    @DeleteMapping("/reset")
    public GameResponse resetGame() {
        //not implemented
        return null;
    }

}
