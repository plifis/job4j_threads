package ru.job4j;

public final class DCLSingleton {
    private static DCLSingleton inst;

    public static DCLSingleton instOf() {
        DCLSingleton temp = inst;
        if (temp != null) {
            return temp;
        } else {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
                return inst;
            }
        }
    }


    private DCLSingleton() {
    }
}