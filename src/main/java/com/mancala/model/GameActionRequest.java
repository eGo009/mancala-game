package com.mancala.model;

public class GameActionRequest {

    public GameActionRequest() {
    }

    public GameActionRequest(int selectedPitNumber) {
        this.selectedPitNumber = selectedPitNumber;
    }

    private int selectedPitNumber;

    public int getSelectedPitNumber() {
        return selectedPitNumber;
    }

    public void setSelectedPitNumber(int selectedPitNumber) {
        this.selectedPitNumber = selectedPitNumber;
    }
}
