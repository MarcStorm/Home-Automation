package COURSE02148.homeautomation.common.tuples;

import COURSE02148.homeautomation.frontend.Command;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

public final class TupleCommand {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("COMMAND"),
            new FormalTemplateField(UUID.class),
            new FormalTemplateField(Command.class),
            new FormalTemplateField(Object.class),
            new FormalTemplateField(String.class),
            new FormalTemplateField(Integer.class)
    );

    public static Tuple create(UUID uuid, Command type, Object media, String frontendHost, int frontendPort){
        return new Tuple("COMMAND", uuid, type, media, frontendHost, frontendPort);
    }

    public static UUID getUUID(Tuple tuple) {
        return (UUID) tuple.getElementAt(1);
    }

    public static Command getType(Tuple tuple){
        return (Command) tuple.getElementAt(2);
    }

    public static Object getMedia(Tuple tuple){
        return (Object) tuple.getElementAt(3);
    }

    public static String getFrontendHost(Tuple tuple){
        return (String) tuple.getElementAt(4);
    }

    public static int getFrontendPort(Tuple tuple){
        return (int) tuple.getElementAt(5);
    }
}
