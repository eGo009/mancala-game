package com.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameActionRequest {

    private int selectedPitNumber;

}
