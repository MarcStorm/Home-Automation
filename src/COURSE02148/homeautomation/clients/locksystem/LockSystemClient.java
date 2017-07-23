package COURSE02148.homeautomation.clients.locksystem;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.locksystem.locks.LockMainDoor;
import COURSE02148.homeautomation.clients.locksystem.view.LockSystemView;

import java.io.IOException;

public class LockSystemClient extends Client {

    private LockSystemView.LockPanel lockPanel;

    public LockSystemClient(String name, String serverHost, int serverPort, int clientPort) throws IOException, InterruptedException {
        super(name, serverHost, serverPort, clientPort);
    }

    public void setLockPanel(LockSystemView.LockPanel lockPanel){
        this.lockPanel = lockPanel;
    }

    public LockSystemView.LockPanel getLockPanel(){
        return lockPanel;
    }
}
