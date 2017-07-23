import COURSE02148.homeautomation.common.tuples.TupleCommand;
import COURSE02148.homeautomation.common.tuples.TupleResponse;
import COURSE02148.homeautomation.frontend.OpusPlayer;
import COURSE02148.homeautomation.server.api.API;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;
import COURSE02148.homeautomation.server.exceptions.ClientCode;
import COURSE02148.homeautomation.server.exceptions.WitResponseException;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.UUID;

public class ServerTest {


    @Test
    public void testVoiceRecognition() throws IOException, WitResponseException {
        File file = new File("frontend/VoiceCommand.WAVE");
        byte[] bytes = Files.toByteArray(file);

        API.WIT.voiceRecognition(bytes);
    }

    @Test
    public void testTextToSpeech() throws Exception {
        String message = "Sorry. I am not connected to the internet";
        byte[] sound = API.IBMWATSON.textToSpeech(message);
        OpusPlayer.play(new ByteArrayInputStream(sound));
        Thread.sleep(3000);
    }

    @Test
    public void testWitResponse() throws JSONException {
        String json = "{\n" +
                "  \"msg_id\" : \"5fc05348-6582-4b25-ab24-fa7b8c700073\",\n" +
                "  \"_text\" : \"Lock the main door\",\n" +
                "  \"outcomes\" : [ {\n" +
                "    \"_text\" : \"Lock the main door\",\n" +
                "    \"confidence\" : 0.985,\n" +
                "    \"intent\" : \"lock\",\n" +
                "    \"entities\" : {\n" +
                "      \"lock\" : [ {\n" +
                "        \"type\" : \"value\",\n" +
                "        \"value\" : \"main_door\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  } ]\n" +
                "}";

        WitResponse wr = new WitResponse(json);

        // WitResponse creates a HashMap of all the values in the JSON object.
        // The print method below print all key/value pairs in the HashMap
        System.out.println(wr);

        // To get a special value from the HashMap you can use a string as a key
        String intent = (String) wr.get("outcomes.intent");

        // To do the same as above, you can use the placeholder for "outcomes.intent" like so
        String theSameIntent = (String) wr.get(Intent.INTENT);

        // To get a different parameter from the JSON object, simply ude another string or enum:
        String text = (String) wr.get(Intent.TEXT);

        // You should always create new parameters in the Intent.class file instead of hardcoding the key
        // E.g. use Intent.INTENT instead of "outcomes.intent"
    }

    @Test
    public void testWitURL() throws UnsupportedEncodingException {
        // URL location of the API
        String context = "DATE";
        String url = "https://api.wit.ai/speech?v=20141022";

        if (context != null){
            String contextObject = URLEncoder.encode("{\"state\":\"" + context + "\"}", "UTF-8");
            url += ("&context=" + contextObject);
        }

        System.out.println(url);
    }

    @Test
    public void testTextToIntent() throws WitResponseException {
        String result = API.WIT.textRecognition("Remind me to do my homework on friday");
        System.out.println(result);

        WitResponse wr = new WitResponse(result);
        System.out.println(wr);
    }

    @Test
    public void createStateJSON() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("REMINDER_TEXT");
        jsonArray.put("CANCEL");

        JsonObject stateObject = new JsonObject();
        stateObject.addProperty("state", "REMINDER");
        stateObject.addProperty("state", "CANCEL");

        System.out.println(stateObject.toString());

    }


    @Test
    public void testWit(){

        try {
            System.out.println(API.WIT.textRecognition("Hello my name is borat"));
        } catch (WitResponseException e) {
            e.printStackTrace();
            System.out.println("It failed");
        }
    }

    @Test
    public void getIP() throws UnknownHostException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
    }


}
