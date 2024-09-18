package BSSProtocol;
import java.rmi.RemoteException;

public interface BSSManagerInterface {
    void send(String message) throws RemoteException;
    void releaseToken() throws RemoteException;
}
