package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

import javax.swing.plaf.TableHeaderUI;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        System.out.println("Нить добавления");
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        final Thread consumer = new Thread(
                () -> {
                        while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            System.out.println("Нить получения");
                            queue.poll();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();

    }
}