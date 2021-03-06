package COURSE02148.homeautomation.clients.locksystem.locks;


import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.Filter;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.clients.locksystem.LockSystemAgent;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;

public class LockKitchenWindow extends LockSystemAgent {

    public LockKitchenWindow(Client client) {
        super(client);
    }

    @Handle(value="lock", sync = true)
    @Filter(filterKey = Intent.LOCK, filterValue = "kitchen_window")
    public void handleLock(WitResponse witResponse) throws InterruptedException {
        lock("Ok. I have locked the kitchen window");
    }

    @Handle(value="lock", sync = true)
    @Filter(filterKey = Intent.LOCK, filterValue = "all")
    public void handleLockAll(WitResponse witResponse) throws InterruptedException {
        lock("Ok. The house has been locked down");
    }

    @Handle(value="unlock", sync = true)
    @Filter(filterKey = Intent.LOCK, filterValue = "kitchen_window")
    public void handleUnlock(WitResponse witResponse){
        unlock("Ok. I have unlocked the kitchen window");
    }


    @Handle(value="unlock", sync = true)
    @Filter(filterKey = Intent.LOCK, filterValue = "all")
    public void handleUnlockAll(WitResponse witResponse) throws InterruptedException {
        unlock("Ok. The house has been unlocked");
    }

}
