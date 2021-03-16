package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;


@ThreadSafe
public class User {
    @GuardedBy("this")
    int id;
    @GuardedBy("this")
    int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public static User of(int id, int amount) {
        return new User(id, amount);
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && amount == user.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
