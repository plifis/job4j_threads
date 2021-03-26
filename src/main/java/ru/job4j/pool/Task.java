package ru.job4j.pool;

public class Task implements Runnable {
    private final String typeJob;

    public Task(String typeJob) {
        this.typeJob = typeJob;
    }

    @Override
    public void run() {
        System.out.println(this.typeJob);
    }

}
