package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(20);
    int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
            for (int i = 0; i < size; i++) {
                threads.add(new Thread(
                        () -> {
                            while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            Runnable runnable = tasks.poll();
                            if (runnable != null) {
                                runnable.run();
                            } else {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                        }
                ));
                threads.get(i).start();
            }
    }

    public void start() {
        while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
            Thread thread = getFreeThread();
        }
    }

    private Thread getFreeThread() {
        for (Thread thread : threads) {
            synchronized (this) {
                if (thread.getState() == Thread.State.WAITING) {
                    return thread;
                }
            }
        }
        return null;
    }

    public void work(Runnable job) {
        tasks.offer(job);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 31; i++) {
            Task task = new Task("jobNumber" + i);
                threadPool.work(task);
        }
        threadPool.shutdown();
    }
}