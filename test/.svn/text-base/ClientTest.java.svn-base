import COURSE02148.homeautomation.clients.mediacenter.MusicPlayer;
import org.junit.Test;

public class ClientTest {

    @Test
    public void playTaylor() throws InterruptedException {
        MusicPlayer player = new MusicPlayer("client/music.wav");
        player.play();
        Thread.sleep(3000);
    }

    @Test
    public void internalClock() throws InterruptedException {
        long time = System.currentTimeMillis();

        int step = 0;
        while(step < 20000){
            time++;
            step++;
            Thread.sleep(1);
        }

        System.out.println("REAL: " + System.currentTimeMillis());
        System.out.println("SIM: " + time);
        System.out.println("DIFF: " + (System.currentTimeMillis() - time));
    }

}
