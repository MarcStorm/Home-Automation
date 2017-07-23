
import java.io.IOException;

import COURSE02148.homeautomation.clients.locksystem.view.LockSystemView;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.SocketPort;
import org.cmg.resp.topology.SocketPortAddress;


public class PingPongLAN {

    public static void main(String[] argv) throws IOException {
        SocketPort pingPort = new SocketPort(8022);
        SocketPort pongPort = new SocketPort(8020);

        if (argv[0].equals("client")){
            // Node ping (client)
            Node pingNode = new Node("ping", new TupleSpace());
            pingNode.addPort(pingPort);
            Agent ping = new PingAgent(argv[1]);
            pingNode.addAgent(ping);
            pingNode.start();
        } else if (argv[0].equals("server")){
            // Node pong (server)
            Node pongNode = new Node("pong", new TupleSpace());
            pongNode.addPort(pongPort);
            Agent pong = new PongAgent(argv[1]);
            pongNode.addAgent(pong);
            pongNode.start();

        }

    }


    public static class PingAgent extends Agent {

        PointToPoint other;

        public PingAgent(String serverIP) {
            super("PING");
            other = new PointToPoint("pong", new SocketPortAddress(serverIP ,9998));;
        }

        @Override
        protected void doRun() {
            try {
                while (true) {
                    System.out.println("PING!");
                    put(new Tuple( "PING" ) , other);
                    System.out.println("PING DONE!");
                    get(new Template(new ActualTemplateField( "PONG" )) , Self.SELF);
                    System.out.println("GET PONG!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static class PongAgent extends Agent {

        PointToPoint other;

        public PongAgent(String clientIP) {
            super("PONG");
            other = new PointToPoint("ping", new SocketPortAddress(clientIP ,9999));
        }

        @Override
        protected void doRun() {
            try {
                while (true) {
                    get(new Template(new ActualTemplateField( "PING" )) , Self.SELF);
                    System.out.println("PONG!");
                    put(new Tuple( "PONG" ) , other);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
