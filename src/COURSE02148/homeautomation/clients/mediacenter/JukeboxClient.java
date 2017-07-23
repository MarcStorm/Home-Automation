package COURSE02148.homeautomation.clients.mediacenter;


import COURSE02148.homeautomation.clients.Client;

import java.io.IOException;

public class JukeboxClient extends Client {
    public JukeboxClient(String serverHost, int serverPort, int clientPort) throws IOException, InterruptedException {
        super("Juke box", serverHost, serverPort , clientPort);
        MediaCenterAgent mediaCenterAgent = new MediaCenterAgent(this);
        clientNode.addAgent(mediaCenterAgent);
    }
}
