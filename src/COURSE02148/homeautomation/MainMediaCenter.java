package COURSE02148.homeautomation;

import COURSE02148.homeautomation.clients.mediacenter.MediaCenterClient;

import java.io.IOException;

public class MainMediaCenter {

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args[0] == null) {
            System.out.println("Please define a server port");
            return;
        }

        MediaCenterClient mediaCenterClient = new MediaCenterClient(args[0], 8080, 8081);
        mediaCenterClient.startClient();
    }
}
