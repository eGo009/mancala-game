package com.mancala.model;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResponse {

    @Nullable
    private String player1Name;
    @Nullable
    private String player2Name;

    private boolean success;

    //a message to be shown to the players
    private String statusMessage;

    @Nullable
    private Pit[] pits;
}
