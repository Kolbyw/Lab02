package BSSProtocol;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            ProcessInterface process0 = (ProcessInterface) Naming.lookup("rmi://localhost/Process0");
            ProcessInterface process1 = (ProcessInterface) Naming.lookup("rmi://localhost/Process1");
            ProcessInterface process2 = (ProcessInterface) Naming.lookup("rmi://localhost/Process2");
            ProcessInterface[] processList = {process0, process1, process2};

            process0.setOthers(new ProcessInterface[]{process1, process2});
            process1.setOthers(new ProcessInterface[]{process0, process2});
            process2.setOthers(new ProcessInterface[]{process1, process0});

            // starting out process0 will have the token
            BSSManager manager = new BSSManager(processList);
            manager.send("Hello from Process0", process0);
            manager.send("Hello from Process1", process1);
            manager.send("Hello from Process2", process2);
            manager.releaseToken();
            manager.releaseToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}