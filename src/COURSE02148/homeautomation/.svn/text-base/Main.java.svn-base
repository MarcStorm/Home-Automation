package COURSE02148.homeautomation;

import COURSE02148.homeautomation.clients.PC.PCClient;
import COURSE02148.homeautomation.clients.locksystem.LockSystemClient;
import COURSE02148.homeautomation.clients.locksystem.LockSystemCluster;
import COURSE02148.homeautomation.clients.locksystem.view.LockSystemView;
import COURSE02148.homeautomation.clients.mediacenter.JukeboxClient;
import COURSE02148.homeautomation.clients.mediacenter.MediaCenterClient;
import COURSE02148.homeautomation.frontend.Frontend;
import COURSE02148.homeautomation.server.Server;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws Exception {

        //Initialise services
        Server server = new Server(8080);
        Frontend frontend = new Frontend(server.serverHost, 8080, 8081);
        MediaCenterClient centerClient = new MediaCenterClient(server.serverHost, 8080, 8082);
//      JukeboxClient jukeboxClient = new JukeboxClient(server.serverHost, 8080, 8083);
        PCClient marcPCClient = new PCClient(server.serverHost, 8080, 8084);
        LockSystemCluster lockSystemCluster = new LockSystemCluster(server.serverHost);

        // Start services
        server.startServer();
        frontend.startFrontend();
        centerClient.startClient();
//      jukeboxClient.startClient();
        marcPCClient.startClient();
        lockSystemCluster.startCluster();
        Timer timer = new Timer();


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                centerClient.stopClient();

                System.out.println("MEDIA CENTER STOPPED!");
            }
        };
        timer.schedule(timerTask,10000);

    }
}
