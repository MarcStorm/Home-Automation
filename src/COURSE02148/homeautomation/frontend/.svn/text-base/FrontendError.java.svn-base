package COURSE02148.homeautomation.frontend;

public enum FrontendError {
    NO_SERVER_CONNECTION("frontend/NoConnectionToServer.opus", "I could not connect to the server, please check your connection."),
    NO_SERVER_FEEDBACK("frontend/NoServerFeedback.opus", "The server did not respond to your request. Please try again later"),
    NO_MICROPHONE_CONNECTION("frontend/NoConnectionToMicrophone.opus", "I could not connect to your microphone, please check your microphone's connection or type a command."),
    ;

    private final String pathname;
    private final String responseText;

    FrontendError(String pathname, String responseText) {
        this.pathname = pathname;
        this.responseText = responseText;
    }

    public String getPathname() {
        return pathname;
    }

    public String getResponseText() {
        return responseText;
    }
}
