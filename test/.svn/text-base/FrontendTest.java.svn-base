import COURSE02148.homeautomation.clients.locksystem.LockSystemCluster;
import COURSE02148.homeautomation.clients.locksystem.view.LockSystemView;
import COURSE02148.homeautomation.clients.mediacenter.MusicPlayer;
import COURSE02148.homeautomation.frontend.OpusPlayer;
import com.google.common.io.Files;
import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FrontendTest {

    private void putBytes(byte[] sourceBytes, byte[] bytesToPut, int position){
        for (byte thisByte : bytesToPut){
            sourceBytes[position] = thisByte;
            position++;
        }
    }

    @Test
    public void testOpusFile() throws IOException {
        File f = new File("frontend/voice.ogg");
        //playOpusFile(new FileInputStream(f));
    }

    @Test
    public void playTest() throws Exception {
        File f = new File("frontend/voice.ogg");
        InputStream in = new FileInputStream(f);
        OpusPlayer.play(in);
    }

    @Test
    public void playOpusConvert() throws InterruptedException {
        MusicPlayer mp = new MusicPlayer("frontend/opus_out.wav");
        mp.play();
        Thread.sleep(3000);
    }

    @Test
    public void lockGUI() throws IOException, InterruptedException {
        LockSystemCluster lockSystemCluster = new LockSystemCluster("127.0.0.1");
        System.in.read();
    }



}
