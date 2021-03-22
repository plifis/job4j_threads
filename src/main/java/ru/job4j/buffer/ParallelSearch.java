package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        System.out.println(queue.poll());
                        System.out.println("Нить получения");
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        System.out.println("Нить добавления");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );
    }
}