package ru.job4j.concurrent;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
            this.readAndWriteFile();
    }

    private void readAndWriteFile() {
        try (BufferedInputStream input = new BufferedInputStream(new URL(this.url).openStream())) {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("temp.txt"));
            int bytesRead;
            long timeStart = System.currentTimeMillis();
            long timeEnd = 0;
            while ((bytesRead = input.read(new byte[1024])) != -1) {
                output.write(bytesRead);
                output.flush();
                timeEnd = System.currentTimeMillis();
                this.validateSpeed(timeStart, timeEnd);
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateSpeed(long start, long end) {
        int realSpeed = (int) (end - start);
        try {
            if (this.speed > realSpeed) {
                Thread.sleep(realSpeed);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }





//    public static void main(String[] args) {
//        Thread thread = new Thread(
//                () -> {
//                    try {
//                        for (int i = 0; i <= 100; i++) {
//                            Thread.sleep(100);
//                            System.out.print("\rLoading : " + i  + "%");
//
//                        }
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//        }
//        );
//        thread.start();
//    }
}
