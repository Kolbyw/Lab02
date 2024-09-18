package BSSProtocol;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try{
            LocateRegistry.createRegistry(1099); // RMI on port 1099 (default)
            int numProcesses = 3;

            for(int x = 0; x < numProcesses; x++) {
                ProcessInterface remoteProcess = new Process(x, numProcesses);
                Naming.rebind("rmi://localhost/Process" + x, remoteProcess);
                System.out.println("Process " + x + " is ready");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}