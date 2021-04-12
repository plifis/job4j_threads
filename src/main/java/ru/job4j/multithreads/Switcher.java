package ru.job4j.multithreads;

public class Switcher {

    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier owner = new MasterSlaveBarrier();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        synchronized (owner) {
                            try {
                                owner.tryMaster();
                            System.out.println("Thread A");
                            owner.doneMaster();
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        synchronized (owner) {
                            try {
                                owner.trySlave();
                            System.out.println("Thread B");
                                owner.doneSlave();
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}