package COURSE02148.homeautomation.common.tuples;


import COURSE02148.homeautomation.clients.Client;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

public final class TupleReady extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("READY"),       // Handle
            new FormalTemplateField(UUID.class),    // Client UUID
            new FormalTemplateField(String.class),  // Client name
            new FormalTemplateField(String.class),  // Client IP
            new FormalTemplateField(Integer.class)  // Client port
    );

    public static Tuple create(UUID clientUUID, String clientName, String clientIP, int clientPort){
        return new Tuple("READY", clientUUID, clientName, clientIP, clientPort);
    }

    public static Tuple create(Client client){
        return new Tuple("READY", client.clientUUID, client.clientName, client.clientHost, client.clientPort);
    }

    public static UUID getClientUUID(Tuple tuple){
        return (UUID) tuple.getElementAt(1);
    }

    public static String getClientName(Tuple tuple){
        return (String) tuple.getElementAt(2);
    }

    public static String getClientHost(Tuple tuple){
        return (String) tuple.getElementAt(3);
    }

    public static int getClientPort(Tuple tuple){
        return (int) tuple.getElementAt(4);
    }

}
