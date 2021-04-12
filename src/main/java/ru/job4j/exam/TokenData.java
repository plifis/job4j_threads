package ru.job4j.exam;

public class TokenData {
    private String value;
    private int ttl;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "value='" + value + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
