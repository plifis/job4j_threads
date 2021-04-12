package ru.job4j;

import org.junit.Test;
import java.util.*;
import java.util.stream.IntStream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class SimpleBlockingQueueTest {
    @Test
    public void add() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread offer = new Thread (
                () -> { for (int i = 0; i < 10; i++) {
                    queue.offer(i);
                    System.out.println("Добавлено" + i);
                }
        }
        );
        Thread poll = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Получено" +  queue.poll());
                    }
                }
        );
        offer.start();
        poll.start();
        offer.join();
        poll.join();
    }

    @Test
    public void whenAdd3ThenPoll3() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> rsl = new ArrayList<>();
        Thread producer = new Thread (
                () -> { for (int i : list) {
                        queue.offer(i);
                    System.out.println("Добавлено" + i);
                }
        }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                           int i = queue.poll();
                           System.out.println("Получено" + i);
                           rsl.add(i);
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(list, rsl);
    }
    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final List<Integer> buffer = new LinkedList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                         //  try {
                               buffer.add(queue.poll());
                              // Thread.sleep(500);
//                           } catch (InterruptedException e) {
//                               e.printStackTrace();
//                               Thread.currentThread().interrupt();
//                           }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

}