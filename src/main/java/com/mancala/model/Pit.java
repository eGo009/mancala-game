package com.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pit {

    private int stonesCount;
    //is the pit selectable for the current player
    private boolean selectable;
}
