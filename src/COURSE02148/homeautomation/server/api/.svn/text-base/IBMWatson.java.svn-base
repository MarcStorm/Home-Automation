package COURSE02148.homeautomation.server.api;

import COURSE02148.homeautomation.server.exceptions.IbmResponseException;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class IBMWatson {

    private static final String USERNAME = "b74e9ac2-2507-4316-a124-e36cdfe6908a";
    private static final String PASSWORD = "lGnK8daoCGdT";

    public IBMWatson(){

    }

    public byte[] textToSpeech(String text) throws IbmResponseException {

        final String url = "https://stream.watsonplatform.net/text-to-speech/api/v1/synthesize";

        HttpRequest request = HttpRequest
                .post(url)
                .contentType("application/json")
                .basicAuthentication(USERNAME, PASSWORD)
                .query("voice", "en-US_AllisonVoice")
                .bodyText("{\"text\":\"" + text + "\"}", "application/json");

        HttpResponse response = request.send();

        if (response.statusCode() != 200) throw new IbmResponseException(response);

        return response.bodyBytes();

    }

}
