package BSSProtocol;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProcessInterface extends Remote {
    void setOthers(ProcessInterface[] processes) throws RemoteException;
    void send(String message) throws RemoteException;
    void deliver(String message) throws RemoteException;
    void receiveMessage(String message, VectorClock senderVectorClock) throws RemoteException;
    void getToken() throws RemoteException;
    void addToQueue(String message) throws RemoteException;
    void printClock() throws RemoteException;
}