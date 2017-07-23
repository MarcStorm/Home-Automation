package COURSE02148.homeautomation.frontend;

import COURSE02148.homeautomation.common.tuples.TupleCommand;
import COURSE02148.homeautomation.common.tuples.TupleFeedback;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.SocketPortAddress;

import javax.sound.sampled.LineUnavailableException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.UUID;


public class FrontendAgent extends Agent {

    private static final int FEEDBACK_TIMEOUT = 10000;
    private PointToPoint pointToServer;
    private Recorder recorder;
    private Tuple feedback;
    private UUID commandUUID;

    public Frontend frontend;

    //Constructor
    public FrontendAgent(Frontend frontend){
        super("Frontend Agent");
        this.pointToServer = new PointToPoint("Server", new SocketPortAddress(frontend.serverHost, frontend.serverPort));
        this.recorder = new Recorder();
        this.frontend = frontend;
    }


    @Override
    protected void doRun() {
        while(true) {

            // Record voice command or text command
            if (!recordVoiceCommand()) continue;

            //Send the voice command to the server.
            if (!sendVoiceCommandToServer()) continue;

            //Get the response from the pointToServer.
            if(!waitForResponseFromServer(FEEDBACK_TIMEOUT)) continue;

            //Play the response message.
            playResponseFromServer(feedback);
        }
    }

    private boolean recordVoiceCommand() {
        try {
            recorder.record();
        } catch (IllegalArgumentException | LineUnavailableException | IOException e) {
            OpusPlayer.play(FrontendError.NO_MICROPHONE_CONNECTION.getPathname());
            return false;
        }
        return true;
    }

    private void playResponseFromServer(Tuple feedback) {
        byte[] soundResponse = TupleFeedback.getVoice(feedback);
        try {
            OpusPlayer.play(new ByteArrayInputStream(soundResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean waitForResponseFromServer(long timeout){
        long step = 100;
        long process = 0;
        while(process < timeout){
            process += step;
            Tuple feedbackTuple = getp(TupleFeedback.TEMPLATE_SPECIFIC(commandUUID));
            if (feedbackTuple == null){
                try {
                    Thread.sleep(step);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            feedback = feedbackTuple;
            return true;
        }
        OpusPlayer.play(FrontendError.NO_SERVER_FEEDBACK.getPathname());
        return false;
    }


    private boolean sendVoiceCommandToServer() {
        Tuple commandTuple = null;
        commandUUID = UUID.randomUUID();
        try {
            commandTuple = TupleCommand.create(commandUUID, recorder.type, recorder.getRecordedMedia(), frontend.frontendHost, frontend.frontendPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            put(commandTuple, this.pointToServer);
        } catch (InterruptedException | SocketException e) {
            OpusPlayer.play(FrontendError.NO_SERVER_CONNECTION.getPathname());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
