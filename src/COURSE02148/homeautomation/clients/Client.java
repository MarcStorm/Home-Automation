package COURSE02148.homeautomation.clients;

import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.SocketPort;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public abstract class Client {

    public String clientName;
    public UUID clientUUID;
    public SocketPort clientSocket;
    public Node clientNode;
    public int serverPort;
    public int clientPort;
    public String clientHost;
    public String serverHost;

    public Client(String name, String serverHost, int serverPort, int clientPort) throws IOException, InterruptedException {
        this.clientName = name;
        this.clientUUID = UUID.randomUUID();
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.serverHost = serverHost;
        this.clientHost = Inet4Address.getLocalHost().getHostAddress();
        System.out.println("CLIENT " + clientName + " RUNNING ON " + clientHost);
        this.clientSocket = new SocketPort(clientHost, clientPort);
        this.clientNode = new Node(clientName, new TupleSpace());
        this.clientNode.addPort(this.clientSocket);
    }

    public void startClient(){
        clientNode.start();
    }

    public void stopClient(){
        clientNode.stop();
    }
}
