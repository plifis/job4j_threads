package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.collection.*;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final SimpleArray<T> array = new SimpleArray<>();

    public synchronized void add(T value) {
        this.array.add(value);
    }

    public synchronized T get(int index) {
        return this.array.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }

    private synchronized SimpleArray<T> copy(SimpleArray<T> array) {
        SimpleArray<T> temp = new SimpleArray<T>();
         array.iterator().forEachRemaining(temp::add);
         return temp;
    }
}