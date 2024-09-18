package BSSProtocol;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Process extends UnicastRemoteObject implements ProcessInterface {
    private int processId;
    private VectorClock vectorClock;
    public List<ProcessInterface> otherProcesses;
    private List<String> messages; // Message Queue
    private List<String> outgoingMessages; // outgoing messages

    public Process(int processId, int totalProcesses) throws RemoteException {
        this.processId = processId;
        this.vectorClock = new VectorClock(totalProcesses);
        this.otherProcesses = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.outgoingMessages = new ArrayList<>();
    }
    @Override
    public void send(String message) throws RemoteException {
        vectorClock.increment(processId); 
        System.out.println("\nProcess " + processId + " sent out this message: '" + message + 
        "'\n\nClocks before message event: ");
        System.out.println("Sending Clock is Clock" + processId + ": ");
        otherProcesses.add(processId, this);
        for (ProcessInterface process : otherProcesses) {
            process.printClock();
        }
        otherProcesses.remove(processId);
        for (ProcessInterface process : otherProcesses) {
            process.receiveMessage(message, vectorClock);
        }
        System.out.println("\nClocks after message event: ");
        otherProcesses.add(processId, this);
        for (ProcessInterface process : otherProcesses) {
            process.printClock();
        }
        otherProcesses.remove(processId);
    }
    @Override
    public void setOthers(ProcessInterface[] processes) {
        for( ProcessInterface process : processes ) {
            otherProcesses.add(process);
        }
    }
    @Override
    public void addToQueue(String message) throws RemoteException {
        outgoingMessages.add(message);
    }
    @Override
    public void deliver(String message) throws RemoteException {
        System.out.println("Process " + processId + " delivered message: " + message);
    }
    @Override
    public void receiveMessage(String message, VectorClock senderVectorClock) throws RemoteException {
        System.out.println("Process " + processId + " received message: '" + message + "'");
        if (!canDeliver(senderVectorClock.getClock())) {
            vectorClock.update(senderVectorClock);
        } else {
            messages.add(message);
        }
    }
    private boolean canDeliver(int[] senderClock) {
        for (int x = 0; x < vectorClock.getClock().length; x++) {
            if (x == processId) {
                if (senderClock[x] != vectorClock.getClock()[x] + 1) {
                    return false;
                }
            } else {
                if (senderClock[x] > vectorClock.getClock()[x]) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void getToken() throws RemoteException {
        System.out.println("\nProcess " + processId + " received the token.");
        this.send(outgoingMessages.get(0));
        outgoingMessages.remove(0);
    }
    @Override
    public void printClock() throws RemoteException {
        System.out.println("Clock" + processId + ": " + this.vectorClock.toString());
        System.out.flush();
    }
}