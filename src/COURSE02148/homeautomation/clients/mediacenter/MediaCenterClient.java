package COURSE02148.homeautomation.clients.mediacenter;

import COURSE02148.homeautomation.clients.Client;

import java.io.IOException;

public class MediaCenterClient extends Client {

    public MediaCenterClient(String serverHost, int serverPort, int clientPort) throws IOException, InterruptedException {
        super("Media center", serverHost, serverPort , clientPort);
        MediaCenterAgent mediaCenterAgent = new MediaCenterAgent(this);
        clientNode.addAgent(mediaCenterAgent);
    }
}
