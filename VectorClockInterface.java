package BSSProtocol;
import java.io.Serializable;

interface VectorClockInterface extends Serializable {
    public int[] getClock(); // Returns internal clock array
    public void increment(int processId); // increment value of clock at processId
    public void update(VectorClock clock); // Compares local clock with remote clock and update with max
    public String toString(); // Returns clock value as string
}