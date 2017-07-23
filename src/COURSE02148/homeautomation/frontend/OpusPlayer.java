package COURSE02148.homeautomation.frontend;


import COURSE02148.homeautomation.clients.mediacenter.MusicPlayer;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;

public class OpusPlayer {

    static String tempFileName = "frontend/converted.wav";
    static String executeable;

    static final String exeMacOSX = "/bin/opus-tools-0.1.9-macos/opusdec";
    static final String exeWindows = "/bin/opus-tools-0.1.9-win32/opusdec.exe";

    public static int play(InputStream fileIn) throws Exception {

        if (executeable == null) return -1;

        ProcessBuilder builder = new ProcessBuilder(System.getProperty("user.dir") + executeable, "-", tempFileName);
        final Process process = builder.start();

        final InputStream is = process.getInputStream();
        final OutputStream processIn = process.getOutputStream();

//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                File file = new File("frontend/converted.wav");
//                FileOutputStream fos;
//                try {
//                    fos = new FileOutputStream(file);
//                } catch (FileNotFoundException e) {
//                    fos = null;
//                    e.printStackTrace();
//                }
//                try {
//                    IOUtils.copy(is, fos);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

        Pipe.pipe(fileIn, processIn);

        processIn.close();
        is.close();

        int returnCode = -1;
        try {
            returnCode = process.waitFor();
        } catch (InterruptedException ex) {
            //--
        }

        MusicPlayer mp = new MusicPlayer(tempFileName);
        mp.play();

        return returnCode;
    }

    public static void initializeOpusDecoder(){
        if (SystemUtils.IS_OS_UNIX){
            executeable = exeMacOSX;
        } else if (SystemUtils.IS_OS_WINDOWS){
            executeable = exeWindows;
        } else {
            throw new UnsupportedOperationException("This operation system does not support voice playback");
        }
    }

    public static void play(String filename) {
        File file = new File(filename);
        InputStream input;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("No such file exist");
            return;
        }

        try {
            play(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
