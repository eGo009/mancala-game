package com.mancala.controller;

import static com.mancala.converter.GameEntitiesConverter.prepareErrorGameResponse;
import static com.mancala.converter.GameEntitiesConverter.prepareSuccessGameResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.exception.UnexpectedGameActionException;
import com.mancala.processor.GameProcessor;
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
    private GameProcessor gameProcessor;

    @PostMapping("/start")
    public GameResponse createGame(@RequestBody CreateGameRequest createGameRequest) {
        gameContext.getPlayer1().setNameIfNotNull(createGameRequest.getPlayer1Name());
        gameContext.getPlayer2().setNameIfNotNull(createGameRequest.getPlayer2Name());
        return tryToConvertGameToResponse();
    }

    @GetMapping("/game")
    public GameResponse getCurrentGame() {
        return tryToConvertGameToResponse();
    }

    @PatchMapping("/action")
    public GameResponse makeAction(@RequestBody GameActionRequest gameActionRequest) {
        ValidationResult validationResult;
        try {
            validationResult = GameValidator.validateSelectedPitNumber(gameContext, gameActionRequest.getSelectedPitNumber());
            if (validationResult.isSuccess()) {
                gameProcessor.makeAction(gameContext, gameActionRequest.getSelectedPitNumber());
                return prepareSuccessGameResponse(gameContext);
            }
        } catch (UnexpectedGameActionException e) {
            validationResult = new ValidationResult(false, e.getMessage());
        }
        return prepareErrorGameResponse(validationResult);
    }

    @DeleteMapping("/reset")
    public GameResponse resetGame() {
        gameContext.setDefaultGameContext();
        return tryToConvertGameToResponse();
    }

    private GameResponse tryToConvertGameToResponse() {
        try {
            return prepareSuccessGameResponse(gameContext);
        } catch (UnexpectedGameActionException e) {
            return prepareErrorGameResponse(new ValidationResult(false, e.getMessage()));
        }
    }

}
