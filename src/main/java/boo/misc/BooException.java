package boo.misc;

public class BooException extends Exception {

    public BooException(String message) {
        super("____________________________________________________________\n" + message
                + "____________________________________________________________\n");
    }
}
