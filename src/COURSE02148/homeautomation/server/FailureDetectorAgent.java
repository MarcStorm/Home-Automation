package COURSE02148.homeautomation.server;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.ClientAgent;
import COURSE02148.homeautomation.clients.PC.PCAgent;
import COURSE02148.homeautomation.common.tuples.TupleFailureDetector;
import COURSE02148.homeautomation.common.tuples.TupleHandle;
import COURSE02148.homeautomation.common.tuples.TupleResendHandlers;
import COURSE02148.homeautomation.server.api.WitResponse;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.SocketPortAddress;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Marcus on 13-01-2016.
 */
public class FailureDetectorAgent extends Agent {
    private PointToPoint pointToServer;
    private WitResponse witResponse;
    //private static final int FAILURE_PERIOD = 5000;
    //protected Timer heartbeat;
    PointToPoint pointToClient;

    Server server;
    TupleSpace serverTupleSpace;
    private HashMap<UUID, Boolean> isSuspected;
    private HashMap<UUID, Tuple> clientInfo;

    public FailureDetectorAgent(Server server) {
        super("Failure Detector Agent");
        this.server = server;
        this.serverTupleSpace = this.server.serverTupleSpace;
        this.pointToServer = new PointToPoint("Server", new SocketPortAddress(server.serverHost, server.serverPort));
    }

    @Override
    protected void doRun() throws Exception {
        isSuspected = new HashMap<UUID, Boolean>();
        Template templateAllHandle = TupleHandle.TEMPLATE_ANY;
        LinkedList<Tuple> clients = serverTupleSpace.queryAll(templateAllHandle);
        clientInfo = new HashMap<UUID, Tuple>();

        // Put earch handle in hasmap isSuspected
        for (Tuple client : clients) {
            UUID clientUUID = TupleHandle.getClientUUID(client);
            isSuspected.put(clientUUID, false);
        }

        Timer timer = new Timer();
        TimerTask resetSuspected = new TimerTask() {
            @Override
            public void run() {
                LinkedList<UUID> suspected = new LinkedList<>();
                Template templateAllHandle = TupleHandle.TEMPLATE_ANY;
                LinkedList<Tuple> clients = serverTupleSpace.queryAll(templateAllHandle);

                for (Tuple client : clients) {
                    UUID clientUUID = TupleHandle.getClientUUID(client);
                    System.out.println("CHECKING " + clientUUID);

                    // If a new client connects to the server, and the handle is not seen before
                    if (isSuspected.get(clientUUID) == null) {
                        /*
                        System.out.println("FOUND SUSPECTED = NULL");
                        Tuple resendHandlers = TupleResendHandlers.resendHandlers();
                        String clientHost = (String) client.getElementAt(5);
                        int clientPort = (int) client.getElementAt(6);
                        PointToPoint pointToClient = new PointToPoint("Client", new SocketPortAddress(clientHost, clientPort));
                        try {
                            System.out.println("TRYING TO RESET HANDLERS");
                            put(resendHandlers,pointToClient);
                            System.out.println("REQUEST SENT");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
                        isSuspected.put(clientUUID, false);
                    }


                    if (isSuspected.get(clientUUID)) {
                        suspected.add(clientUUID);
                        System.out.println("SUSPECT " + clientUUID);
                    }
                }

                for (UUID clientUUID : suspected) {
                    Template template = TupleHandle.templateUUID(clientUUID);
                    serverTupleSpace.getAll(template);
                    isSuspected.remove(clientUUID);
                }

                for (Tuple client : clients) {
                    UUID clientUUID = TupleHandle.getClientUUID(client);
                    System.out.println("RESETTING");
                    isSuspected.put(clientUUID, true);
                }
                System.out.println("DONE");

                //Create for-loop that iterates UUID's in isSuspectedMap

                /*for (UUID clientUUID : isSuspected.keySet()) {
                    Template template = TupleHandle.templateUUID(clientUUID);
                    Tuple tuple = getp(template);
                    if (tuple == null && clientInfo.get(clientUUID) != null) {
                        System.out.println("FOUND SUSPECTED = NULL");
                        Tuple clientInfoTuple = clientInfo.get(clientUUID);
                        Tuple resendHandlers = TupleResendHandlers.resendHandlers();
                        String clientHost = (String) clientInfoTuple.getElementAt(2);

                        int clientPort = (Integer) clientInfoTuple.getElementAt(3);
                        System.out.println(clientHost + " - " + clientPort);
                        pointToClient = new PointToPoint("Client", new SocketPortAddress(clientHost, clientPort));

                            System.out.println("TRYING TO RESET HANDLERS");
                            if(resendHandlers == null) System.out.println("RESETHANDLERS == NULL");
                            if(pointToClient == null) System.out.println("POINTTOCLIENT == NULL");
                            try{
                                put(resendHandlers, pointToClient);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            System.out.println("REQUEST SENT");

                        isSuspected.put(clientUUID, false);
                    }
                }*/
            }
        };
        timer.scheduleAtFixedRate(resetSuspected, 0, 10000);
        while (true) {
            //Template template = new Template(new ActualTemplateField("FAILURE_DETECTOR"),new FormalTemplateField(UUID.class),new FormalTemplateField(Integer.class));
            Template template = TupleFailureDetector.TEMPLATE;
            System.out.println("LOOKING");
            Tuple tuple = get(template, pointToServer);
            System.out.println("FOUND SOMETING");
            UUID clientUUID = (UUID) tuple.getElementAt(1);
            System.out.println("FOUND " + clientUUID);
            isSuspected.put(clientUUID, false);
            clientInfo.put(clientUUID, tuple);
        }

    }
}
    /*
    private void setCheckTimer(){
        Timer timer = new Timer();
        TimerTask resetSuspected = new TimerTask() {
            @Override
            public void run() {
                LinkedList<UUID> suspected = new LinkedList<>();
                Template templateAllHandle = TupleHandle.TEMPLATE_ANY;
                LinkedList<Tuple> clients = serverTupleSpace.queryAll(templateAllHandle);


                for(Tuple client : clients){
                    UUID clientUUID = (UUID) client.getElementAt(3);
                    System.out.println("CHECKING " + clientUUID);
                    if(isSuspected.get(clientUUID) == null) continue;
                    if(isSuspected.get(clientUUID)){
                        suspected.add(clientUUID);
                        System.out.println("SUSPECT " + clientUUID);
                    }
                    isSuspected.put(clientUUID,true);
                }
            }
        };
        timer.scheduleAtFixedRate(resetSuspected,0,15000);
    }
    */


