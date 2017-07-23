package COURSE02148.homeautomation.server.serverhandler;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.server.Server;

import java.io.IOException;

public class ServerHandleClient extends Client {

    public Server server;

    public ServerHandleClient(Server server) throws IOException, InterruptedException {
        super("Server Handler", server.serverHost, server.serverPort, 8079);
        this.server = server;
        ServerHandleAgent serverHandleAgent = new ServerHandleAgent(this);
        clientNode.addAgent(serverHandleAgent);
    }
}
