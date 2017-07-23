package COURSE02148.homeautomation.common.tuples;

import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

public final class TupleFeedback extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("FEEDBACK"),
            new FormalTemplateField(UUID.class),
            new FormalTemplateField(byte[].class),
            new FormalTemplateField(String.class)
    );

    public static Template TEMPLATE_SPECIFIC(UUID uuid) {
        return new Template(
                new ActualTemplateField("FEEDBACK"),
                new ActualTemplateField(uuid),
                new FormalTemplateField(byte[].class),
                new FormalTemplateField(String.class)
        );
    }

    private TupleFeedback() {
        throw new UnsupportedOperationException();
    }

    public static Tuple create(UUID uuid, byte[] voiceMessage, String responseText){
        return new Tuple("FEEDBACK", uuid, voiceMessage, responseText);
    }

    public static UUID getUUID(Tuple tuple) {
        return (UUID) tuple.getElementAt(1);
    }

    public static byte[] getVoice(Tuple tuple){
        return (byte[]) tuple.getElementAt(2);
    }

    public static String getText(Tuple tuple){
        return (String) tuple.getElementAt(3);
    }

}
