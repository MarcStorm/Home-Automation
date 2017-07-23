package COURSE02148.homeautomation.common.tuples;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.Filter;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.server.api.Intent;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.SocketPortAddress;

import java.util.UUID;


public final class TupleHandle extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("HANDLE"),      // Handle
            new FormalTemplateField(String.class),  // Command
            new FormalTemplateField(Intent.class),  // Filter key
            new FormalTemplateField(String.class),  // Filter value
            new FormalTemplateField(UUID.class),    // Client UUID
            new FormalTemplateField(String.class),  // Client name
            new FormalTemplateField(String.class),  // Client IP
            new FormalTemplateField(Integer.class), // Client port
            new FormalTemplateField(Boolean.class)  // Sync
    );

    public static Tuple create(Handle handle, Intent filterKey, String filterValue, UUID clientUUID, String clientName, String clientIP, int clientPort){
        return new Tuple("HANDLE", handle.value(), filterKey, filterValue, clientUUID, clientName, clientIP, clientPort, handle.sync());
    }

    public static Tuple create(Handle handle, UUID clientUUID, String clientName, String clientIP, int clientPort){
        return new Tuple("HANDLE", handle.value(), Intent.NO_FILTER, "", clientUUID, clientName, clientIP, clientPort, handle.sync());
    }

    public static Tuple create(Handle handle, Client client){
        return new Tuple("HANDLE", handle.value(), Intent.NO_FILTER, "", client.clientUUID, client.clientName, client.clientHost, client.clientPort, handle.sync());
    }

    public static Tuple create(Handle handle, Filter filter, Client client){
        return new Tuple("HANDLE", handle.value(), filter.filterKey(), filter.filterValue(), client.clientUUID, client.clientName, client.clientHost, client.clientPort, handle.sync());
    }

    public static Template templateCommand(String command) {
        return new Template(
                new ActualTemplateField("HANDLE"),      // Handle
                new ActualTemplateField(command),       // Command
                new FormalTemplateField(Intent.class),  // Filter key
                new FormalTemplateField(String.class),  // Filter value
                new FormalTemplateField(UUID.class),    // Client UUID
                new FormalTemplateField(String.class),  // Client name
                new FormalTemplateField(String.class),  // Client host
                new FormalTemplateField(Integer.class), // Client port
                new FormalTemplateField(Boolean.class)  // Sync
        );
    }

    public static Template templateUUID(UUID uuid){
        return new Template(
                new ActualTemplateField("HANDLE"),      // Handle
                new FormalTemplateField(String.class),  // Command
                new FormalTemplateField(Intent.class),  // Filter key
                new FormalTemplateField(String.class),  // Filter value
                new ActualTemplateField(uuid),          // Client UUID
                new FormalTemplateField(String.class),  // Client name
                new FormalTemplateField(String.class),  // Client host
                new FormalTemplateField(Integer.class), // Client port
                new FormalTemplateField(Boolean.class)  // Sync
        );
    }

    public static Template templateClient(String name, String host, int port){
        return new Template(
                new ActualTemplateField("HANDLE"),      // Handle
                new FormalTemplateField(String.class),  // Command
                new FormalTemplateField(Intent.class),  // Filter key
                new FormalTemplateField(String.class),  // Filter value
                new FormalTemplateField(UUID.class),    // Client UUID
                new ActualTemplateField(name),          // Client name
                new ActualTemplateField(host),          // Client host
                new ActualTemplateField(port),          // Client port
                new FormalTemplateField(Boolean.class)  // Sync
        );
    }

    public static String getCommand(Tuple tuple){
        return (String) tuple.getElementAt(1);
    }

    public static Intent getFilterKey(Tuple tuple){
        return (Intent) tuple.getElementAt(2);
    }

    public static String getFilterValue(Tuple tuple) {
        return (String) tuple.getElementAt(3);
    }

    public static UUID getClientUUID(Tuple tuple){
        return (UUID) tuple.getElementAt(4);
    }

    public static String getClientName(Tuple tuple){
        return (String) tuple.getElementAt(5);
    }

    public static String getClientHost(Tuple tuple){
        return (String) tuple.getElementAt(6);
    }

    public static int getClientPort(Tuple tuple){
        return (int) tuple.getElementAt(7);
    }

    public static boolean getIsSync(Tuple tuple){
        return (boolean) tuple.getElementAt(8);
    }

    public static PointToPoint getPointToPoint(Tuple tuple){
        return new PointToPoint(getClientName(tuple), new SocketPortAddress(getClientHost(tuple), getClientPort(tuple)));
    }
}
