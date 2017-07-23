package COURSE02148.homeautomation.common.tuples;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.Handle;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

/**
 * Created by Marcus on 14-01-2016.
 */
public class TupleFailureDetector
{
    public static final Template TEMPLATE = new Template(new ActualTemplateField("FAILURE_DETECTOR"),
            new FormalTemplateField(UUID.class),
            new FormalTemplateField(String.class),
            new FormalTemplateField(Integer.class)
    );

    public static Tuple create(Client client){
        return new Tuple("FAILURE_DETECTOR",client.clientUUID,client.clientHost,client.clientPort);
    }

}
