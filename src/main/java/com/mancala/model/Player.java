package com.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {

    private String name;
    private int startPitNumber;
    private int storePitNumber;


    /**
     * Check if the pitNumber belongs to given player's and it's not a store pit.
     *
     * @param pitNumber pit number to be checked
     * @return true if the pitNumber belongs to given player's and it's not a store pit.
     */
    public boolean isActivePit(int pitNumber) {
        return pitNumber >= getStartPitNumber() && pitNumber < getStorePitNumber();
    }

    public void setNameIfNotNull(String name) {
        if (name != null) {
            this.name = name;
        }
    }
}
