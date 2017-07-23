package COURSE02148.homeautomation.server.exceptions;

public class FoundResponseException extends Exception {

    public FoundResponseException() {
        super("A handler was found to process your request");
    }
}
