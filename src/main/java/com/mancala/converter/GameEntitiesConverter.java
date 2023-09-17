package com.mancala.converter;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mancala.exception.UnexpectedGameActionException;
import com.mancala.model.GameContext;
import com.mancala.model.GameResponse;
import com.mancala.model.GameState;
import com.mancala.model.Pit;
import com.mancala.validator.ValidationResult;

public class GameEntitiesConverter {

    public static GameResponse prepareSuccessGameResponse(GameContext gameContext) throws UnexpectedGameActionException {
        return new GameResponse(
                gameContext.getPlayer1().getName(),
                gameContext.getPlayer2().getName(),
                true,
                createStatusMessage(gameContext),
                preparePitsInfo(gameContext)
        );
    }

    public static GameResponse prepareErrorGameResponse(ValidationResult validationResult) {
        return new GameResponse(
                null,
                null,
                false,
                validationResult.getErrorMessage(),
                null
        );
    }

    private static Pit[] preparePitsInfo(GameContext gameContext) throws UnexpectedGameActionException {
        Pit[] pits = new Pit[gameContext.getPits().length];
        for (int i = 0; i < gameContext.getPits().length; i++) {
            pits[i] = new Pit(
                    gameContext.getPits()[i],
                    gameContext.getState() != GameState.FINISHED && !gameContext.isEmptyPit(i) && gameContext.getCurrentPlayer().isActivePit(i));
        }
        return pits;
    }

    private static String createStatusMessage(GameContext gameContext) throws UnexpectedGameActionException {
        if (gameContext.getState() == GameState.FINISHED) {
            return StringUtils.isEmpty(gameContext.getWinnerName()) ? "Draw" : String.format("%s is winner", gameContext.getWinnerName());
        }
        return String.format("%s turn", gameContext.getCurrentPlayer().getName());
    }
}
