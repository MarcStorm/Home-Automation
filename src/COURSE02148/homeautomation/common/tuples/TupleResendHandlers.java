package COURSE02148.homeautomation.common.tuples;

import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;

import java.util.UUID;

/**
 * Created by Marcus on 14-01-2016.
 */
public class TupleResendHandlers {
    public static final Template TEMPLATE_ANY = new Template(
            new ActualTemplateField("RESEND_HANDLERS")     // Handle
    );

    public static Tuple resendHandlers(){
        return new Tuple("RESEND_HANDLERS");
    }
}
