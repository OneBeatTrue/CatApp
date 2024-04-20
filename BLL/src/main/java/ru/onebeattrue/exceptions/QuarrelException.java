package ru.onebeattrue.exceptions;

public class QuarrelException extends NullPointerException {
    public QuarrelException(Long firstCatId, Long secondCatId) {
        super("Cats " + firstCatId + " " + secondCatId + " not friends");
    }
}