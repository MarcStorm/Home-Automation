package COURSE02148.homeautomation.server.serverhandler;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.ClientAgent;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.server.api.WitResponse;

public class ServerHandleAgent extends ClientAgent {

    public ServerHandleAgent(Client client) {
        super(client);
    }

    @Handle("creator")
    public void handleCreator(WitResponse witResponse){
        putResponse("This application was developed by Marc, Mathias and Marcus for the course distributed applications at DTU");
    }

    @Handle("greeting")
    public void handleGreeting(WitResponse witResponse){
        putResponse("Hello there!");
    }

    @Handle("cake")
    public void handleCake(WitResponse witResponse){
        putResponse("Unfortunately Simon did not bring cake today although he is overdue. I'm sorry");
    }

    @Handle("age")
    public void handleAge(WitResponse witResponse){
        putResponse("Well. Since you asked. I don't have an age. I'm a computer. Remember?");
    }

    @Handle("mood")
    public void handleMood(WitResponse witResponse){
        putResponse("Thanks for asking. Since I'm a computer I'm always happy. Well, for the most part at least");
    }

    @Handle("thanks")
    public void handleThanks(WitResponse witResponse){
        putResponse("No problem");
    }

    @Handle("cancel")
    public void handleCancel(WitResponse witResponse){
        putResponse("No problem. Get back to me another time");
    }

}
