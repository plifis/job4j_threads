package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenExecute2Thread() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        User userOne = new User(1, 100);
        User userTwo = new User(2, 100);
        userStorage.add(userOne);
        userStorage.add(userTwo);
        Thread first = new Thread(
                () -> userStorage.transfer(1, 2, 50)
        );
        Thread second = new Thread(
                () -> userStorage.transfer(1, 2, 70)
        );
        first.start();
        first.join();
        second.start();
        first.join();
        userStorage.transfer(1, 2, 120);
        assertThat(userStorage.getUser(2).getAmount(), is(220));
    }

    @Test
    public void whenAdd2ThenGet2() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        User userOne = new User(1, 100);
        User userTwo = new User(2, 100);
        Thread first = new Thread(
                () ->         userStorage.add(userOne)
        );
        Thread second = new Thread(
                () ->         userStorage.add(userTwo)
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(userStorage.getUser(1), is(userOne));
        assertThat(userStorage.getUser(2), is(userTwo));
    }

    @Test (expected = NullPointerException.class)
    public void whenAdd2AndDelete1ThenGetNull() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        User userOne = new User(1, 100);
        User userTwo = new User(2, 100);
        Thread first = new Thread(
                () ->         userStorage.add(userOne)
        );
        Thread second = new Thread(
                () ->         userStorage.add(userTwo)
        );
        Thread third  = new Thread(
                () -> userStorage.delete(userOne)
        );
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        assertNull(userStorage.getUser(1));
    }
}