package COURSE02148.homeautomation.common.tuples;

import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

public final class TupleGo extends Tuple {

    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("GO"),
            new FormalTemplateField(Long.class),
            new FormalTemplateField(Long.class));

    public static Tuple create(long startTime){
        return new Tuple("GO", System.currentTimeMillis(), startTime);
    }

    public static long getServerTime(Tuple tuple){
        return (long) tuple.getElementAt(1);
    }

    public static long getStartTime(Tuple tuple){
        return (long) tuple.getElementAt(2);
    }

}
