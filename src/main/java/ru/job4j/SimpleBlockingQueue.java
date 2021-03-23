package ru.job4j;

import jdk.dynalink.linker.support.SimpleLinkRequest;
import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int total;

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    public SimpleBlockingQueue() {
        this.total = 3;
    }

    public synchronized void offer(T value) {
        while (queue.size() >= total) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T t = queue.poll();
        notifyAll();
        return t;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
