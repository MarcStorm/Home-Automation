package COURSE02148.homeautomation.frontend;

import com.google.common.io.Files;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Marc on 06/01/16.
 */
public class Recorder {

    TargetDataLine microphone;
    AudioFileFormat.Type fileType;
    File outputFile;

    AudioFormat format;

    DataLine.Info info;
    ByteArrayOutputStream out;
    SimpleAudioRecorder simpleRecorder;

    String message;
    Command type;

    boolean stopRecording;

    public Recorder() {
        this.microphone = null;
        //8k SampleRate, 16 sample size in bits, 1 channel (mono), signed, littleEndian.
        this.format = new AudioFormat(8000, 16, 1, true, false);
        this.info = new DataLine.Info(TargetDataLine.class, this.format);
        this.out = new ByteArrayOutputStream();
        this.stopRecording = true;

        fileType = AudioFileFormat.Type.WAVE;
        outputFile = new File("frontend/VoiceCommand.WAVE");
    }

    public void record() throws IOException, LineUnavailableException {

        System.out.println("Press ENTER to start the recording");
        System.out.println("or start typing to send a text message instead");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if (!line.isEmpty()){
            message = line;
            System.out.println("Your text message was recorded");
            type = Command.TEXT;
            return;
        }

        startRecording();
        System.out.println("Press ENTER to stop the recording.");
        scanner.nextLine();
        stopRecording();
        type = Command.VOICE;

    }

    public void startRecording() throws IOException, LineUnavailableException {

        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);

        simpleRecorder = new SimpleAudioRecorder(
                microphone,
                fileType,
                outputFile);

		// Recording started.
        simpleRecorder.start();
        System.out.println("Recording...");
    }

    public void stopRecording() {
		// Recording stopped.
        simpleRecorder.stopRecording();
        System.out.println("Recording stopped.");
    }

    public byte[] getAudioCommand() throws IOException {
        return Files.toByteArray(outputFile);
    }

    public Object getRecordedMedia() throws IOException {
        if (type.equals(Command.TEXT)){
            return message;
        } else {
            return Files.toByteArray(outputFile);
        }
    }
}
