package com.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {

    private String name;
    private int startPitNumber;
    private int storePitNumber;

    public boolean isActivePit(int pitNumber) {
        return pitNumber >= getStartPitNumber() && pitNumber < getStorePitNumber();
    }

    public void setNameIfNotNull(String name) {
        if (name != null) {
            this.name = name;
        }
    }
}
