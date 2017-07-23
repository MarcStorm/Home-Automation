package COURSE02148.homeautomation.server.exceptions;

import jodd.http.HttpResponse;

public class IbmResponseException extends Exception {

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    private HttpResponse httpResponse;

    public IbmResponseException(HttpResponse httpError) {
        super("HTTP request could not connect to IBM Watson service");
        this.httpResponse = httpError;
    }
}
