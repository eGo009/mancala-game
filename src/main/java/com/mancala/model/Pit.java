package com.mancala.model;

public class Pit {

    private int stonesCount;
    private boolean selectable;

    public Pit(int stonesCount, boolean selectable) {
        this.stonesCount = stonesCount;
        this.selectable = selectable;
    }

    public int getStonesCount() {
        return stonesCount;
    }

    public void setStonesCount(int stonesCount) {
        this.stonesCount = stonesCount;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
