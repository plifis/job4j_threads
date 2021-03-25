package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdd2AndUpdateThenGetUpdate() {
        Base first = new Base(1, 1);
        first.setName("first");
        Base second = new Base(2, 1);
        second.setName("second");
        Base update = new Base(1, 1);
        update.setName("update");
        Cache cache = new Cache();
        cache.add(first);
        cache.add(second);
        cache.update(update);
        assertThat(cache.getElement(1).getName(), is("update"));
    }

    @Test
    public void whenAdd2AndDelete1ThenGetNull() {
        Base first = new Base(1, 1);
        Base second = new Base(2, 1);
        Cache cache = new Cache();
        cache.add(first);
        cache.add(second);
        cache.delete(first);
        assertNull(cache.getElement(1));
    }
}