package COURSE02148.homeautomation.common.tuples;

import COURSE02148.homeautomation.server.exceptions.ClientCode;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

public final class TupleResponse extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("RESPONSE"),
            new FormalTemplateField(ClientCode.class),
            new FormalTemplateField(UUID.class),
            new FormalTemplateField(String.class));

    public static Tuple create(ClientCode responseCode, UUID clientUUID, String result){
        return new Tuple("RESPONSE", responseCode, clientUUID, result);
    }

    public static ClientCode getClientCode(Tuple tuple){
        return (ClientCode) tuple.getElementAt(1);
    }

    public static UUID getClientUUID(Tuple tuple){
        return (UUID) tuple.getElementAt(2);
    }

    public static String getResult(Tuple tuple){
        return (String) tuple.getElementAt(3);
    }

}
