package COURSE02148.homeautomation.server.exceptions;

import jodd.http.HttpResponse;

public class WitResponseException extends Exception {

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    HttpResponse httpResponse;

    public WitResponseException(HttpResponse httpResponse) {
        super("HTTP call to Wit API was not successful");
        this.httpResponse = httpResponse;
    }
}
