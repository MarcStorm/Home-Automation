import COURSE02148.homeautomation.server.api.API;
import COURSE02148.homeautomation.server.exceptions.IbmResponseException;
import COURSE02148.homeautomation.server.exceptions.WitResponseException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marc on 12/01/16.
 */
public class LocalVoiceGenerator {

    @Test
    public void generateVoiceResponse() throws IOException, WitResponseException, IbmResponseException {
        String systemLocation = "frontend";
        String voiceResponseName = "NoServerFeedback";
        String voiceResponse = "The server did not respond to your request. Please try again later";
        byte[] responseVoice = API.IBMWATSON.textToSpeech(voiceResponse);

        FileUtils.writeByteArrayToFile(new File(systemLocation+"/"+voiceResponseName+".opus"), responseVoice);
    }
}
