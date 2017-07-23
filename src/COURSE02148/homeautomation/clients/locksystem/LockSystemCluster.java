package COURSE02148.homeautomation.clients.locksystem;

import COURSE02148.homeautomation.clients.locksystem.locks.LockKitchenWindow;
import COURSE02148.homeautomation.clients.locksystem.locks.LockMainDoor;
import COURSE02148.homeautomation.clients.locksystem.view.LockSystemView;

import java.io.IOException;
import java.util.ArrayList;

public class LockSystemCluster {

    public ArrayList<LockSystemClient> lockClients;

    public LockSystemCluster(String serverHost) throws IOException, InterruptedException {

        lockClients = new ArrayList<>();

        // Main door
        LockSystemClient lockMainDoor = new LockSystemClient("Lock main door", serverHost, 8080, 8085);
        lockMainDoor.clientNode.addAgent(new LockMainDoor(lockMainDoor));
        lockClients.add(lockMainDoor);

        // Kitchen window
        LockSystemClient lockKitchenWindow = new LockSystemClient("Lock kitchen window", serverHost, 8080, 8086);
        lockKitchenWindow.clientNode.addAgent(new LockKitchenWindow(lockKitchenWindow));
        lockClients.add(lockKitchenWindow);

        LockSystemView lockSystemView = new LockSystemView(this);
    }

    public void startCluster() {
        for(LockSystemClient client : lockClients){
            client.startClient();
        }
    }
}
