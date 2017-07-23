package COURSE02148.homeautomation.clients.PC;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.ClientAgent;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.Context;
import COURSE02148.homeautomation.server.api.WitResponse;


public class PCAgent extends ClientAgent {

    public PCAgent(Client client) {
        super(client);
    }

    @Handle("display")
    public void handleDisplay(WitResponse witResponse) {

        System.out.println(123);
        putResponse("Message was sent");

    }

    @Handle("reminder")
    public void handleReminder(WitResponse witResponse){

        String date = (String) witResponse.get(Intent.DATETIME);
        if (date == null){
            putState(Context.DATE, "When do you want to be reminded?");
            return;
        }

        // If the reminder text is supplied separately, copy this to the reminder
        witResponse.copy(Intent.MESSAGEBODY, Intent.REMINDER);

        String reminder = (String) witResponse.get(Intent.REMINDER);
        if (reminder == null || reminder.isEmpty()){
            putState(Context.MESSAGE, "What do you want to be reminded of?");
            return;
        }

        putResponse("Ok. Your reminder has been added");
    }
}
