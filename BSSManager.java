package BSSProtocol;
import java.rmi.RemoteException;

public class BSSManager {
    private ProcessInterface[] processList;
    private int currProcess;
    private int numProcesses;

    public BSSManager(ProcessInterface[] processes) {
        currProcess = 0;
        numProcesses = processes.length;
        processList = processes;
    }
    public void send(String message, ProcessInterface sendingProcess) throws RemoteException {
        if(sendingProcess.equals(processList[currProcess])) {
            sendingProcess.send(message);
        }
        else {
            sendingProcess.addToQueue(message);
        }
    }
    public void releaseToken() throws RemoteException {
        System.out.println("Releasing token.");
        currProcess = (currProcess + 1) % numProcesses;
        processList[currProcess].getToken();
    }
}
