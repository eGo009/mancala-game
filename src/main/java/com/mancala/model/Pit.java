package com.mancala.model;

public class Pit {

    private int stonesCount;
    private boolean selectable;

    public Pit(int stonesCount, boolean selectable) {
        this.stonesCount = stonesCount;
        this.selectable = selectable;
    }
}
