package COURSE02148.homeautomation.frontend;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.SocketPort;

import java.io.IOException;
import java.net.Inet4Address;

public class Frontend {

    String frontendName;
    int frontendPort;
    Node frontendNode;
    Agent frontendAgent;
    SocketPort frontendSocket;
    public String frontendHost;

    public String serverHost;
    public int serverPort;

    public Frontend(String serverHost, int serverPort, int frontendPort) throws IOException {
        this.frontendName = "Frontend";
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.frontendHost = Inet4Address.getLocalHost().getHostAddress();
        this.frontendPort = frontendPort;
        this.frontendSocket = new SocketPort(this.frontendHost, this.frontendPort);
        this.frontendNode = new Node(this.frontendName, new TupleSpace());
        this.frontendNode.addPort(frontendSocket);
        this.frontendAgent = new FrontendAgent(this);
        this.frontendNode.addAgent(this.frontendAgent);

        OpusPlayer.initializeOpusDecoder();
    }

    public void startFrontend(){
        frontendNode.start();
    }
}
