package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        InputStream i = new FileInputStream(file);
        StringBuffer buffer = new StringBuffer();
        int data;
        while ((data = i.read()) > -1) {
            buffer.append((char) data);
        }
        return buffer.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        InputStream i = new FileInputStream(file);
        StringBuffer buffer = new StringBuffer();
        int data;
        while ((data = i.read()) > -1) {
            if (data < 0x80) {
                buffer.append((char) data);
            }
        }
        return buffer.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }
}