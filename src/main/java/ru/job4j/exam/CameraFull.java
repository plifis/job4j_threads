package ru.job4j.exam;

public class CameraFull {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private int ttl;

    @Override
    public String toString() {
        return "CameraFull{" +
                "id=" + id +
                ", urlType='" + urlType + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", value='" + value + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
