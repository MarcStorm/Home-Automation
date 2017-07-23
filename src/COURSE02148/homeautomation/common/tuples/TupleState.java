package COURSE02148.homeautomation.common.tuples;

import COURSE02148.homeautomation.server.api.Context;
import COURSE02148.homeautomation.server.api.WitResponse;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

public final class TupleState extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("STATE"),
            new FormalTemplateField(Context.class),
            new FormalTemplateField(WitResponse.class)
    );

    public static Tuple create(Context state, WitResponse witResponse){
        return new Tuple("STATE", state, witResponse);
    }

    public static Context getState(Tuple tuple){
        return (Context) tuple.getElementAt(1);
    }

    public static WitResponse getWitResponse(Tuple tuple){
        return (WitResponse) tuple.getElementAt(2);
    }
}
