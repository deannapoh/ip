package boo.misc;

/**
 * Represents an exception that occurred in the Chatbot.
 */
public class BooException extends Exception {

    /**
     * Constructs an error message if chatbot throws an exception.
     *
     * @param message Message shown to user when exception occurs.
     */
    public BooException(String message) {
        super("____________________________________________________________\n" + message
                + "____________________________________________________________\n");
    }
}
