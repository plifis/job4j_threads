package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        System.out.println("Нить добавления");
                        queue.offer(index);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        final Thread consumer = new Thread(
                () -> {
                        while (producer.isAlive()) {
                            System.out.println("Нить получения");
                            queue.poll();
                        }
                }
        );
        consumer.start();
    }
}