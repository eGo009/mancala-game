package com.mancala.model;

public class Player {

    private String name;
    private int startPitNumber;
    private int storePitNumber;

    public Player(String name, int startPitNumber, int storePitNumber) {
        this.name = name;
        this.startPitNumber = startPitNumber;
        this.storePitNumber = storePitNumber;
    }

    public String getName() {
        return name;
    }

    public void setNameIfNotNull(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public int getStartPitNumber() {
        return startPitNumber;
    }

    public int getStorePitNumber() {
        return storePitNumber;
    }
}
