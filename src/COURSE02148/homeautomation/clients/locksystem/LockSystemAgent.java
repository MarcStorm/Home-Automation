package COURSE02148.homeautomation.clients.locksystem;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.ClientAgent;
import COURSE02148.homeautomation.clients.Filter;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;

import java.awt.*;
import java.util.Random;

public abstract class LockSystemAgent extends ClientAgent {

    public LockSystemAgent(Client client) {
        super(client);
    }

    protected void lock(String response) throws InterruptedException {
        // Simulate initialization
        /*Random random = new Random();
        Thread.sleep(random.nextInt(2000));*/

        //waitForSync();

        LockSystemClient lockSystemClient = (LockSystemClient) client;
        lockSystemClient.getLockPanel().setBackground(Color.RED);
        putResponse(response);
    }

    protected void unlock(String response){
        LockSystemClient lockSystemClient = (LockSystemClient) client;
        lockSystemClient.getLockPanel().setBackground(Color.GREEN);
        putResponse(response);
    }
}
