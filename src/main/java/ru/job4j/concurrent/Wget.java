package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i <= 100; i++) {
                            Thread.sleep(100);
                            System.out.print("\rLoading : " + i  + "%");

                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
        }
        );
        thread.start();
    }
}
