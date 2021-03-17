package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("monitor")
    private final Object monitor = false;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            if (count != total) {
                monitor.wait();
            } else {
                monitor.notifyAll();
            }
        }
    }
}
