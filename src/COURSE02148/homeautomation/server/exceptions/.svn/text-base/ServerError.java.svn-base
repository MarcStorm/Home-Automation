package COURSE02148.homeautomation.server.exceptions;

public enum ServerError {
    INTERNET_ERROR("server/NoConnectionToOnlineServices.opus", "I could not connect to online services, please try again later."),
    SERVICE_ERROR("server/WrongInputToWit.opus", "Internal online service error, please contact administrator."),
    API_ERROR_ALL_FAILED("server/APIErrorAllFailed.opus", "I could not connect to online services, please contact administrator. Your command was not executed on any clients."),
    API_ERROR_SOME_FAILED("server/APIErrorSomeFailed.opus", "I could not connect to online services, please contact administrator. Your command was not executed on one or more clients."),
    API_ERROR_ALL_SUCCEEDED("server/APIErrorAllSucceeded.opus", "I could not connect to online services, please contact administrator. Your command was executed on all the clients."),
    WATSON_ERROR("server/WatsonError.opus", "I could not connect to speech synthesis service, please contact administrator. Your command may not have been handled.");

    private final String pathname;
    private final String responseText;

    ServerError(String pathname, String responseText) {
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
