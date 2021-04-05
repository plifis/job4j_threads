package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;


/**
 * Пул потоков.
 * Создается список потоков по количеству доступных процессоров в системе.
 * Каждый поток поток получает задания пока в Очереди (блокирующая) есть доступные задания
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(20);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
            for (int i = 0; i < size; i++) {
                threads.add(new Thread(
                        () -> {
                            while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            Runnable runnable = tasks.poll();
                                runnable.run();
                        }
                        }
                ));
                threads.get(i).start();
            }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            Task task = new Task("jobNumber" + i);
                threadPool.work(task);
        }
        threadPool.shutdown();
    }
}