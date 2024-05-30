package ru.onebeattrue.exceptions;

public class BusyUsernameException extends NullPointerException {
    public BusyUsernameException(String username) {
        super("Username " + username + " is busy");
    }
}