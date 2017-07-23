package COURSE02148.homeautomation;

import COURSE02148.homeautomation.clients.locksystem.LockSystemCluster;
import COURSE02148.homeautomation.clients.mediacenter.MediaCenterClient;

import java.io.IOException;

public class MainLockCluster {

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length == 0) {
            System.out.println("Please define a server host");
            return;
        }

        LockSystemCluster lockSystemCluster = new LockSystemCluster(args[0]);
        lockSystemCluster.startCluster();
    }
}
