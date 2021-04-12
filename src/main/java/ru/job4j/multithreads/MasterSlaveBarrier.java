package ru.job4j.multithreads;


public class MasterSlaveBarrier {
    final MasterSlaveBarrier owner = this;
    boolean master = true;

    public void tryMaster() throws InterruptedException {
        synchronized (owner) {
            while (!master) {
                owner.wait();
            }

        }
    }

    public void trySlave() throws InterruptedException {
        synchronized (owner) {
            while (master) {
                owner.wait();
            }
        }
    }

    public void doneMaster() {
        synchronized (owner) {
            master = false;
            owner.notifyAll();
        }
    }

    public void doneSlave() {
        synchronized (owner) {
            master = true;
            owner.notifyAll();
        }
    }
}
