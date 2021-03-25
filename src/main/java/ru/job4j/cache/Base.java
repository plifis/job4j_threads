package ru.job4j.cache;

import java.util.concurrent.atomic.AtomicReference;

public class Base {
    private final int id;
    private final AtomicReference<Integer> version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = new AtomicReference<>(version);
    }

    public int getId() {
        return id;
    }

    public AtomicReference<Integer> getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}