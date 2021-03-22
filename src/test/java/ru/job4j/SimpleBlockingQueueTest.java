//package ru.job4j;
//
//import org.apache.log4j.pattern.LineSeparatorPatternConverter;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Queue;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertEquals;
//
//
//public class SimpleBlockingQueueTest {
//    @Test
//    public void add() throws InterruptedException {
//        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
//        Thread offer = new Thread (
//                () -> { for (int i = 0; i < 10; i++) {
//                    queue.offer(i);
//                    System.out.println("Добавлено" + i);
//                }
//        }
//        );
//        Thread poll = new Thread(
//                () -> {
//                    for (int i = 0; i < 10; i++) {
//                        System.out.println("Получено" +  queue.poll());
//                    }
//                }
//        );
//        offer.start();
//        poll.start();
//        offer.join();
//        poll.join();
//    }
//
//    @Test
//    public void whenAdd3ThenPoll3() throws InterruptedException {
//        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
//        List<Integer> list = List.of(1, 2, 3, 4, 5, 6);
//        List<Integer> rsl = new ArrayList<>();
//        Thread offer = new Thread (
//                () -> { for (int i : list) {
//                        queue.offer(i);
//                    System.out.println("Добавлено" + i);
//                }
//        }
//        );
//        Thread poll = new Thread(
//                () -> {
//                    while (!queue.isEmpty()) {
//                        int i = queue.poll();
//                        System.out.println("Получено" + i);
//                        rsl.add(i);
//                    }
//                }
//        );
//        offer.start();
//        poll.start();
//        offer.join();
//        poll.join();
//        assertEquals(list, rsl);
//    }
//
//}