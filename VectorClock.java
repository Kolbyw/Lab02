package BSSProtocol;

import java.util.Arrays;

public class VectorClock implements VectorClockInterface {
    private int[] clock;

    public VectorClock(int numProcesses) {
        clock = new int[numProcesses];
    }

    @Override 
    public synchronized int[] getClock() {
        return clock.clone();
    }
    @Override
    public synchronized void increment(int processId) {
        clock[processId]++;
    }
    @Override
    public synchronized void update(VectorClock remoteClock) {
        int[] newClock = remoteClock.getClock();
        for(int x = 0; x < clock.length; x++) {
            clock[x] = Math.max(clock[x], newClock[x]);
        }
    }
    @Override
    public String toString() {
        return Arrays.toString(clock);
    }
}