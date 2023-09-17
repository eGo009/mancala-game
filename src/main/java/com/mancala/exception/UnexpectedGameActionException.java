package com.mancala.exception;

public class UnexpectedGameActionException extends Exception {

    public UnexpectedGameActionException(String message) {
        super("Unexpected game action: " + message);
    }
}
