package COURSE02148.homeautomation.clients.mediacenter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MusicPlayer {

    private Clip clip;

    public MusicPlayer(String filename){

        // Open sound file as InputStream
        try {
            initSound(filename);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public MusicPlayer(InputStream sound){
        try {
            initSound(sound);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(){
        if (clip.isRunning()){
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    private void initSound(String filename) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File soundFile = new File(filename);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioIn);
    }

    private void initSound(InputStream inputStream) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(inputStream);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioIn);
    }
}
