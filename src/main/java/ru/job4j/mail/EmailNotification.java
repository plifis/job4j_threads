package ru.job4j.mail;


import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public EmailNotification() {
    }

    private String patternLetter(User user) {
        StringBuilder builder = new StringBuilder();
            builder.append("subject = Notification ").append(user.getUsername())
                    .append("to email ").append(user.getEmail()).append(".").append(System.lineSeparator());
            builder.append("body = Add a new event to ").append(user.getUsername());
            return builder.toString();
    }

    public void emailTo(User user) {
        pool.submit(
                () -> {
                    patternLetter(user);
                }
        );
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
    pool.shutdown();
    while (pool.isTerminated()) {
        try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }
    }
}
