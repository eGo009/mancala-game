package com.mancala.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player = new Player("test", 0, 6);

    @Test
    public void isActivePitShouldReturnTrueIfPitBelongsToPlayerAndNotStorePit() {
        assertTrue(player.isActivePit(5));
    }

    @Test
    public void isActivePitShouldReturnFalseIfPitBelongsToPlayerAndStorePit() {
        assertFalse(player.isActivePit(6));
    }

    @Test
    public void isActivePitShouldReturnFalseIfPitNotBelongsToPlayer() {
        assertFalse(player.isActivePit(7));
    }

    @Test
    public void setNameIfNotNullShouldNotSetNull() {
        player.setNameIfNotNull(null);
        assertNotNull(player.getName());
    }

    @Test
    public void setNameIfNotNullShouldSetNotNullValue() {
        player.setNameIfNotNull("new");
        assertEquals("new", player.getName());
    }
}
