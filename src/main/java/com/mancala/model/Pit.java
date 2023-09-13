package com.mancala.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pit pit = (Pit) o;
        return stonesCount == pit.stonesCount &&
                selectable == pit.selectable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stonesCount, selectable);
    }
}
