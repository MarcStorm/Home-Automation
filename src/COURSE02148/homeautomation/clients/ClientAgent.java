package COURSE02148.homeautomation.clients;

import COURSE02148.homeautomation.common.tuples.*;
import COURSE02148.homeautomation.server.api.Context;
import COURSE02148.homeautomation.server.api.Intent;
import COURSE02148.homeautomation.server.api.WitResponse;
import COURSE02148.homeautomation.server.exceptions.ClientCode;
import com.google.common.collect.ArrayListMultimap;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.SocketPortAddress;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ClientAgent extends Agent {

    private PointToPoint pointToServer;
    public Client client;
    private ArrayListMultimap<String, Method> handlers;
    private WitResponse witResponse;
    private Timer failureDetectorTimer;
    private static final int FAILURE_PERIOD = 10000;

    private Timer stopOtherTimerTimer;
    private Timer startAgainTimer;

    public ClientAgent(Client client) {
        super("Client Agent");
        this.handlers = ArrayListMultimap.create();
        this.client = client;
        this.pointToServer = new PointToPoint("Server", new SocketPortAddress(client.serverHost, client.serverPort));
    }

    private Template templateIntent = new Template(new ActualTemplateField("INTENT"),
            new FormalTemplateField(WitResponse.class));

    // Intent, server time, server start time (server time + a defined offset)
    private Template templateGo = new Template(new ActualTemplateField("GO"),
            new FormalTemplateField(Long.class),
            new FormalTemplateField(Long.class));

    @Override
    protected void doRun() throws Exception {

        initialiseHandlers();
        createTimerForFailureDetector();

            while(true){
                // Look for incoming commands
                Tuple requestTuple = get(templateIntent, Self.SELF);
                witResponse = (WitResponse) requestTuple.getElementAt(1);

                // Invoke the responsible method for handling the command
                invokeHandler(witResponse);
            }

    }

    public void putResponse(String result){
        try {
            Tuple tupleResponse = TupleResponse.create(ClientCode.OK, client.clientUUID, result);
            put(tupleResponse, pointToServer);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void putState(Context state, String question){
        try {
            Tuple stateTuple = TupleState.create(state, witResponse);
            put(stateTuple, pointToServer);
            putResponse(question);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean initialiseHandlers(){
        Class myClass = this.getClass();

        for (Method method : myClass.getDeclaredMethods()){

            Handle handle = null;
            Filter filter = null;

            for (Annotation annotation : method.getAnnotations()){
                if (annotation instanceof Handle){
                    handle = (Handle) annotation;
                }
                if (annotation instanceof Filter){
                    filter = (Filter) annotation;
                }
            }

            if (handle == null) continue;

            handlers.put(handle.value(), method);
            if (!sendHandleToServer(handle, filter)) return false;
        }
        return true;
    }

    private boolean sendHandleToServer(Handle handle, Filter filter){
        // Create appropriate handle
        Tuple handleTuple;
        if (filter == null){
            handleTuple = TupleHandle.create(handle, client);
        } else {
            handleTuple = TupleHandle.create(handle, filter, client);
        }

        // Send handle to server
        try {
            put(handleTuple, pointToServer);
        } catch (InterruptedException | IOException e) {
            System.out.println("CANT SEND HANDLE");
            return false;
        }
        return true;
    }

    private void invokeHandler(WitResponse witResponse){
        String command = (String) witResponse.get(Intent.INTENT);
        List<Method> methods = handlers.get(command);

        for (Method method : methods){

            if (method == null){
                putResponse("Client had no implementation to handle your request. Please inform the administrators");
                continue;
            }

            Filter filter = method.getAnnotation(Filter.class);
            if (filter != null){
                boolean filterOk = witResponse.get(filter.filterKey()).equals(filter.filterValue());
                if (!filterOk) continue;
            }

            try {
                System.out.println("METHOD INVOKED!");
                method.invoke(this, witResponse);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                putResponse("The client was subject to an internal error. Please inform the administrators");
            }
        }

    }

    public long waitForSync() {
        Tuple tuple = TupleReady.create(client);
        try {
            put(tuple, pointToServer);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        Tuple tupleGO = null;
        try {
            tupleGO = get(templateGo, Self.SELF);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        //server time, server start time (server time + a defined offset)
        long serverTime = (long) TupleGo.getServerTime(tupleGO);
        long serverStartTime = (long) TupleGo.getStartTime(tupleGO);

        //Calculate clients offset in perspective to the server.
        long clientOffset = System.currentTimeMillis() - serverTime;

        //Calculate the local time for the client when to start the execution.
        return serverStartTime - clientOffset;
}
    private void createTimerForFailureDetector(){
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                timerTask();
            }
        };
        this.failureDetectorTimer = new Timer();
        TimerTask spotOtherTimerTask = new TimerTask() {
            @Override
            public void run() {
                failureDetectorTimer.cancel();
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                timerTask();
            }
        };
        //startAgainTimer = new Timer();        stopOtherTimerTimer = new Timer();
        failureDetectorTimer.scheduleAtFixedRate(task,0, FAILURE_PERIOD);
        //stopOtherTimerTimer.schedule(spotOtherTimerTask,5000);

        //startAgainTimer.scheduleAtFixedRate(task2,30000,FAILURE_PERIOD);
    }

    public void timerTask(){
        System.out.println("I AM RUNNING !!!");
        Tuple tuple = TupleFailureDetector.create(this.client);
        try {
            put(tuple,pointToServer);
            System.out.println("i put a tuple in for the failure detector to find");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            resendHandlers();
            System.out.println("HANDLERS WAS RESENT");
        }
        Tuple resendTuple = getp(TupleResendHandlers.TEMPLATE_ANY);
        if(resendTuple != null){
            initialiseHandlers();
        }
    }

    private void resendHandlers() {
        handlers.clear();
        while(!initialiseHandlers()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}