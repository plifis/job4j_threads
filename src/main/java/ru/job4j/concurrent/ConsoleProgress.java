package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\rload: " + "\\");
            Thread.sleep(300);
            System.out.print("\rload: " + "|");
            Thread.sleep(300);
            System.out.print("\rload: " + "/");
            Thread.sleep(300);
        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
