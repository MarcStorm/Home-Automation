package COURSE02148.homeautomation.server.exceptions;

public class HandlingCompleteException extends Exception {

    public HandlingCompleteException() {
        super("There was no more handlers to process");
    }
}
