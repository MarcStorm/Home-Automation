package COURSE02148.homeautomation.clients.PC;

import COURSE02148.homeautomation.clients.Client;

import java.io.IOException;

/**
 * Created by Marc on 08/01/16.
 */
public class PCClient extends Client {

    public PCClient(String serverHost, int serverPort, int clientPort) throws IOException, InterruptedException {
        super("PC", serverHost, serverPort, clientPort);
        PCAgent pcAgent = new PCAgent(this);
        clientNode.addAgent(pcAgent);
    }
}
