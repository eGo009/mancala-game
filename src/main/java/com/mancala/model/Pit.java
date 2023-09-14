package com.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pit {

    private int stonesCount;
    private boolean selectable;
}
