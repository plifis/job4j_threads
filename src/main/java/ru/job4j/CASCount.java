package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer temp;
        Integer nextStep;
        do {
            temp = count.get();
            nextStep = temp + 1;
        }
        while (!count.compareAndSet(temp, nextStep));
    }

    public int get() {
       return count.get();
    }
}