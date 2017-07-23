package COURSE02148.homeautomation.server;


import COURSE02148.homeautomation.common.tuples.*;
import COURSE02148.homeautomation.server.api.API;
import COURSE02148.homeautomation.server.api.Context;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;
import COURSE02148.homeautomation.server.exceptions.*;
import jodd.http.HttpException;
import org.apache.commons.io.IOUtils;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.SocketPortAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.*;

public class ServerAgent extends Agent {

    PointToPoint pointToFrontend;
    PointToPoint ptp;

    private Tuple tupleCommand;
    private Object commandMedia;

    TupleSpace serverTupleSpace;
    String responseText;
    byte[] responseVoice;

    Tuple responseTuple;
    UUID responseUUID;

    WitResponse witResponse;

    // All awaiting tasks
    HashMap<UUID, Tuple> awaitingHandlers;

    // Completed normal tasks
    HashMap<UUID, Tuple> completedHandlers;

    // Sync tasks in ready/completed state
    HashMap<UUID, Tuple> syncReadyHandlers;
    HashMap<UUID, Tuple> syncCompletedHandlers;

    long serverSyncTime;

    private Timer failureDetector;
    private static final int CHECK_CLIENTS_PERIOD = 10000;
    private static final double CONFIDENCE_THRESHOLD = 0.20;

    public ServerAgent(TupleSpace serverTupleSpace) {
        super("Server Agent");
        this.serverTupleSpace = serverTupleSpace;
    }

    @Override
    protected void doRun() throws InterruptedException, IOException {
        while (true){

            System.out.println("SERVER READY FOR COMMAND");
            // Get voice command from frontend
            getCommandFromFrontend();

            System.out.println("SERVER GOT COMMAND");

            // Set point to point back to frontend
            createPointToFrontend(tupleCommand);

            // Check for active states (may be null for no active state)
            Tuple tupleState = serverTupleSpace.getp(TupleState.TEMPLATE_ANY);

            System.out.println("SENDING REQUEST TO WIT");

            // Sends the request to WIT and converts the answer from WIT to a WitResponse object.
            if (!requestToWit(commandMedia, tupleState)) continue;

            System.out.println("CHECKING FOR INTENT");

            // Check if the recorded voice message has any meaning to it
            if (!commandHasIntent()) continue;

            // Check if the confidence is high enough.
            if (!confidenceIsHighEnough(witResponse)) continue;

            // Check for previous dialogues and retrieve information
            checkActiveDialogs(witResponse, tupleState);

            System.out.println("ROUTING TO CLIENT");

            // Send command to appropriate client
            if (!routeCommandToClient(witResponse)) continue;

            // Send result back to frontend
            sendResponseToFrontend();
        }
    }

    private void getCommandFromFrontend() throws InterruptedException, IOException {
        tupleCommand = get(TupleCommand.TEMPLATE_ANY, Self.SELF);
        commandMedia = TupleCommand.getMedia(tupleCommand);
        // Get the UUID from the command tuple and prepare it for return with the response tuple.
        responseUUID = TupleCommand.getUUID(tupleCommand);
    }

    private void createPointToFrontend(Tuple tupleCommand) {
        pointToFrontend = new PointToPoint("Frontend", new SocketPortAddress(
                TupleCommand.getFrontendHost(tupleCommand),
                TupleCommand.getFrontendPort(tupleCommand))
        );
    }

    private boolean commandHasIntent() throws IOException, InterruptedException {
        // No intent was generated!
        if (!witResponse.hasIntent()){
            responseText = "I don't know what you mean";
            sendResponseToFrontend();
            return false;
        }
        return true;
    }

    private boolean requestToWit(Object commandMedia, Tuple tupleState) throws IOException, InterruptedException {
        String intent;
        try {
            intent = API.WIT.mediaRecognition(commandMedia, getState(tupleState));
        // Exception for when server could not connect to WIT.
        } catch (HttpException e) {
            sendLocalResponseToFrontend(ServerError.INTERNET_ERROR);
            return false;
        } catch (WitResponseException e) {
            sendLocalResponseToFrontend(ServerError.SERVICE_ERROR);
            return false;
        }

        // Parse the intent from JSON
        witResponse = new WitResponse(intent);
        // Display the intent (only for debug)
        System.out.println(intent);
        return true;
    }

    private boolean confidenceIsHighEnough(WitResponse witResponse) throws IOException, InterruptedException {
        if ((double)witResponse.get(Intent.CONFIDENCE) < CONFIDENCE_THRESHOLD) {
            responseText = "I'm not completely sure of what you mean.";
            sendResponseToFrontend();
            return false;
        }
        return true;
    }

    private void sendResponseToFrontend() throws IOException, InterruptedException {
        try {
            responseVoice = API.IBMWATSON.textToSpeech(responseText);
        } catch (IbmResponseException e) {
            if (hasAllTasksFailed()){
                // All tasks failed
                sendLocalResponseToFrontend(ServerError.WATSON_ERROR);
                return;
            } else if (hasUnhandledTasks()){
                // Some tasks failed
                sendLocalResponseToFrontend(ServerError.WATSON_ERROR);
                return;
            } else {
                // All tasks succeeded
                sendLocalResponseToFrontend(ServerError.WATSON_ERROR);
                return;
            }
        }

        Tuple feedback = TupleFeedback.create(responseUUID, responseVoice, responseText);

        put(feedback, pointToFrontend);
    }

    private void sendLocalResponseToFrontend(ServerError serverError) throws IOException, InterruptedException {
        File file = new File(serverError.getPathname());
        InputStream input = new FileInputStream(file);
        byte[] responseVoice = IOUtils.toByteArray(input);
        Tuple feedback = TupleFeedback.create(responseUUID, responseVoice, serverError.getResponseText());
        put(feedback, pointToFrontend);
    }

    private void checkActiveDialogs(WitResponse witResponse, Tuple tupleState) {
        if (tupleState == null) return;
        if (!witResponse.hasIntent()) {
            restoreState(tupleState);
            return;
        }
        if (witResponse.get(Intent.INTENT).equals("cancel")) return;
        WitResponse oldWitResponse = (WitResponse) tupleState.getElementAt(2);
        witResponse.merge(oldWitResponse);
    }

    private void restoreState(Tuple tupleState){
        try {
            put(tupleState, Self.SELF);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private Context getState(Tuple tupleState){
        if (tupleState == null) return null;
        return (Context) tupleState.getElementAt(1);
    }

    public boolean routeCommandToClient(WitResponse witResponse) throws IOException, InterruptedException {
        String command = (String) witResponse.get(Intent.INTENT);
        LinkedList<Tuple> handles = serverTupleSpace.queryAll(TupleHandle.templateCommand(command));

        // Filter the handles for filter key and filter value
        filterHandles(handles, witResponse);

        // No client to handle the command
        if (handles.isEmpty()) {
            responseText = "I could not find a service to handle your request";
            sendResponseToFrontend();
            return false;
        }

        System.out.println("NUMBER OF CLIENTS: " + handles.size());
        System.out.println(handles);

        // Found client, sending command to client for processing
        System.out.println("SEND INTENT TO CLIENTS");
        sendCommandToClient(handles, witResponse);

        serverSyncTime = System.currentTimeMillis();

        // Get all normal responses. and put awaiting handlers -> sync ready handlers
        System.out.println("START HANDLE RESPONSES");
        handleResponses(3000, handles);

        // Send GO tuple to all handlers in sync ready handlers
        System.out.println("START SYNC TASKS");
        startSynchronisedTasks();

        System.out.println("CLIENTS NO ANSWER: " + awaitingHandlers.size());
        System.out.println("CLIENT SYNC COUNT: " + syncReadyHandlers.size());

        // Wait for sync task responses. Sync ready handlers -> sync completed handlers
        waitForSyncResponses(3000);

        System.out.println("CLIENTS UNABLE TO START: " + syncReadyHandlers.size());
        System.out.println("CLIENTS COMPLETED: " + syncCompletedHandlers.size());

        if (responseTuple == null){
            responseText = "Error. The client did not respond to your request";
            sendResponseToFrontend();
            return false;
        }

        responseText = TupleResponse.getResult(responseTuple);
        return true;

    }

    private void filterHandles(LinkedList<Tuple> handles, WitResponse witResponse) {
        System.out.println("HANDLES BEFORE FILTER: " + handles.size());

        ListIterator iterator = handles.listIterator();

        while (iterator.hasNext()){
            Tuple handle = (Tuple) iterator.next();
            Intent filterKey = TupleHandle.getFilterKey(handle);
            if (filterKey.equals(Intent.NO_FILTER)) continue;
            String filterValue = TupleHandle.getFilterValue(handle);
            String witValue = (String) witResponse.get(filterKey);
            System.out.println("KEY: " + filterKey.getKey() + " VALUE: " + filterValue + " WITVALUE: " + witValue);
            //if (witValue.equals("all")) continue;
            if (filterValue.equals(witValue)) continue;
            iterator.remove();
            System.out.println("HANDLE FILTERED OUT!");
        }
    }

    private void startSynchronisedTasks(){
        // If there are no sync tasks to perform, return
        if (syncReadyHandlers.isEmpty()) return;

        for (Map.Entry<UUID, Tuple> entry : syncReadyHandlers.entrySet()){
            Tuple handle = entry.getValue();
            PointToPoint pointClient = TupleHandle.getPointToPoint(handle);

            Tuple tupleGo = TupleGo.create(serverSyncTime + 5000);
            // Put tuple in client tuplespace
            try {
                put(tupleGo, pointClient);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForSyncResponses(long timeout){
        if (syncReadyHandlers.isEmpty()) return;

        long step = 100;
        long process = 0;
        while(process < timeout){

            Tuple responseTuple = serverTupleSpace.getp(TupleResponse.TEMPLATE_ANY);
            if (responseTuple != null){
                UUID clientUUID = TupleResponse.getClientUUID(responseTuple);
                Tuple handle = syncReadyHandlers.remove(clientUUID);
                syncCompletedHandlers.put(clientUUID, handle);
                if (syncReadyHandlers.isEmpty()) break;
                continue;
            }

            // Sleep
            process += step;
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseTuple = TupleResponse.create(
                ClientCode.OK,
                UUID.randomUUID(),
                "Okay. I completed your request on " + syncCompletedHandlers.size() + " clients"
        );
    }

    public void sendCommandToClient(LinkedList<Tuple> handles, WitResponse witResponse) {
        for (Tuple handlerTuple : handles){
            ptp = TupleHandle.getPointToPoint(handlerTuple);

            try {
                put(new Tuple("INTENT", witResponse), ptp);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void handleResponses(long timeout, LinkedList<Tuple> handles){
        // Initialise HashMaps for responses
        awaitingHandlers = new HashMap<>();
        completedHandlers = new HashMap<>();
        syncCompletedHandlers = new HashMap<>();
        syncReadyHandlers = new HashMap<>();

        // Add all handlers as awaiting responses
        for (Tuple handle : handles){
            UUID clientUUID = TupleHandle.getClientUUID(handle);
            awaitingHandlers.put(clientUUID, handle);
        }

        long step = 100;
        long process = 0;
        while(process < timeout){

            // Handling for normal tasks
            try {
                retrieveTupleResponse();
            } catch (HandlingCompleteException e) {
                break;
            } catch (FoundResponseException e) {
                continue;
            }

            // Handling for synchronized tasks
            try {
                retrieveSyncReadyResponse();
            } catch (HandlingCompleteException e) {
                break;
            } catch (FoundResponseException e) {
                continue;
            }

            // Sleep
            try {
                process += step;
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void retrieveTupleResponse() throws HandlingCompleteException, FoundResponseException {
        // Attempt to retrieve tuple
        Tuple response = serverTupleSpace.getp(TupleResponse.TEMPLATE_ANY);
        if (response == null) return;

        // Remove handler from awaiting handlers
        this.responseTuple = response;
        UUID clientUUID = TupleResponse.getClientUUID(response);
        Tuple handle = awaitingHandlers.remove(clientUUID);
        completedHandlers.put(clientUUID, handle);

        // Throw exceptions based on the outcome
        if (awaitingHandlers.isEmpty()) throw new HandlingCompleteException();
        throw new FoundResponseException();
    }

    private void retrieveSyncReadyResponse() throws HandlingCompleteException, FoundResponseException {
        // Attempt to retrieve tuple
        Tuple tupleReady = serverTupleSpace.getp(TupleReady.TEMPLATE_ANY);
        if (tupleReady == null) return;

        // If found, remove from awaiting handlers and put in ready handlers

        UUID clientUUID = TupleReady.getClientUUID(tupleReady);
        Tuple handle = awaitingHandlers.remove(clientUUID);
        syncReadyHandlers.put(clientUUID, handle);

        // Throw exceptions based on the outcome
        if (awaitingHandlers.isEmpty()) throw new HandlingCompleteException();
        throw new FoundResponseException();
    }


    private boolean hasUnhandledTasks(){
        return !awaitingHandlers.isEmpty() || !syncReadyHandlers.isEmpty();
    }

    private boolean hasAllTasksFailed(){
        return completedHandlers.isEmpty() && syncCompletedHandlers.isEmpty();
    }
}