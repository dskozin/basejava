package ru.dskozin.resumeapp.deadlock;

public class Fork {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();

        taken = true;
    }

    public synchronized void drop(){
        taken = false;
        notifyAll();
    }
}
