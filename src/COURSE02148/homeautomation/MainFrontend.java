package COURSE02148.homeautomation;

import COURSE02148.homeautomation.frontend.Frontend;

import java.io.IOException;

public class MainFrontend {

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Please define a server port");
            return;
        }

        String serverHost = args[0];
        System.out.println(serverHost);
        Frontend frontend = new Frontend(serverHost, 8080, 8080);
        frontend.startFrontend();

    }
}
