package ru.job4j.pools;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParallelIndexSearchTest {
    @Test
    public void whenAddArrayIntegerThenGetIndex() {
        Integer[] arr = new Integer[] {4, 5, 7, 9, 2, 0, 11, 1, 3, 6, 8, 12, 14, 15, 16};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<Integer>(arr, 5);
        assertThat(search.compute(), is(1));
    }

    @Test
    public void whenAddArrayStringThenGetIndex() {
        String[] arr = new String[]{"pc", "mouse", "key", "car", "desk"};
        ParallelIndexSearch<String> search = new ParallelIndexSearch<String>(arr, "key");
        assertThat(search.compute(), is(2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenAddArrayIntThenException() {
        Integer[] arr = new Integer[] {4, 5, 7, 9, 2, 0, 11, 1, 3, 6, 8, 12, 14, 15, 16};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<Integer>(arr, 99);
        search.compute();
    }

}