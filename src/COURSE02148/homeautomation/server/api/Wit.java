package COURSE02148.homeautomation.server.api;

import COURSE02148.homeautomation.server.exceptions.WitResponseException;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.StringJoiner;

public class Wit {


    private static final String TOKEN = "ECJIRMJLL67GICJ3C36TCVDJT45WRFTH";

    public String voiceRecognition(byte[] voiceFile) throws WitResponseException {
        return voiceRecognition(voiceFile, null);
    }

    public String mediaRecognition(Object media, Context context) throws WitResponseException {
        // Text commands
        if (media instanceof String){
            String commandText = (String) media;
            return textRecognition(commandText, context);
        }

        // Voice commands
        if (media instanceof byte[]){
            byte[] commandVoice = (byte[]) media;
            return voiceRecognition(commandVoice, context);
        }

        // Unsupported command format
        throw new IllegalArgumentException("Media not supported");
    }

    public String textRecognition(String text, Context context) throws WitResponseException {
        final String url = "https://api.wit.ai/message";

        HttpRequest request = HttpRequest
                .get(url)
                .header("Authorization", "Bearer " + TOKEN)
                .query("v", "20141022")
                .query("q", text);

        String state = getStateJSON(context);
        if (state != null) {
            request.query("context", state);
        }

        HttpResponse response = request.send();

        if (response.statusCode() != 200) throw new WitResponseException(response);
        return response.bodyText();
    }

    public String textRecognition(String text) throws WitResponseException {
        return textRecognition(text, null);
    }

    public String voiceRecognition(byte[] voiceFile, Context context) throws WitResponseException {

        final String url = "https://api.wit.ai/speech?v=20141022";

        HttpRequest request = HttpRequest
                .post(url)
                .contentType("audio/wav")
                .header("Authorization", "Bearer " + TOKEN)
                .body(voiceFile, "audio/wav");

        String state = getStateJSON(context);
        if (state != null) {
            request.query("context", state);
        }

        HttpResponse response = request.send();

        if (response.statusCode() != 200) throw new WitResponseException(response);
        return response.bodyText();
    }

    private String getStateJSON(Context context){
        if (context == null) return null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"state\":");

        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");

        stringJoiner.add("\"" + context.toString() + "\"");
        stringJoiner.add("\"" + Context.CANCEL.toString() + "\"");

        stringBuilder.append(stringJoiner);
        stringBuilder.append("}");

        return stringBuilder.toString();
    }



}
