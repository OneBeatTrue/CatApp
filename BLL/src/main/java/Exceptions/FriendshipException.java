package Exceptions;

public class FriendshipException extends NullPointerException {
    public FriendshipException(Long firstCatId, Long secondCatId) {
        super("Cats " + firstCatId + " " + secondCatId + " already friends");
    }
}