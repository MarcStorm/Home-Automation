package COURSE02148.homeautomation.clients.mediacenter;

import COURSE02148.homeautomation.clients.Client;
import COURSE02148.homeautomation.clients.ClientAgent;
import COURSE02148.homeautomation.clients.Handle;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MediaCenterAgent extends ClientAgent {

    static MusicPlayer player;

    public MediaCenterAgent(Client client) {
        super(client);
    }

    @Handle(value = "play_music", sync = true)
    public void playMusic(WitResponse witResponse) {

        // Prepare the play of music (not needed at the moment, as the music played is static)
        //initialisationMusic();
        if (player == null){
            player = new MusicPlayer("client/music.wav");
        }

        // Simulate initialization part
        Random random = new Random();
        int randomInt = random.nextInt(2000);
        System.out.println("MUSIC INIT: " + randomInt + " MILISECONDS");
        try {
            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send tuple back to server, with "READY" and what client is responding.
        // Wait for tuple with "GO".
        long startTime = waitForSync();

        // Start the play.
        postSyncMusic(witResponse, startTime);
    }

    //No initialisation for the music player is needed at the moment.
    /*private void initialisationMusic() {
    }*/

    private void postSyncMusic(WitResponse witResponse, long startTime) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String onOff = (String) witResponse.get(Intent.ONOFF);

                if (onOff.equals("on")){
                    player.play();
                    System.out.println("MEDIA: PLAYING MUSIC");
                } else if (onOff.equals("off")){
                    player.stop();
                    System.out.println("MEDIA: STOPPING MUSIC");
                }
            }
        };
        timer.schedule(timerTask, new Date(startTime));
        String onOff = (String) witResponse.get(Intent.ONOFF);

        if (onOff.equals("on")){
            putResponse("Okay. I started the music on " + client.clientName);
        } else if (onOff.equals("off")){
            putResponse("Okay. I stopped the music on " + client.clientName);
        }

    }
}