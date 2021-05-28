package ru.job4j.exam;

/**
 * Класс камеры описываемый с помощью идентификатора, источника данных и токена камеры
 */

public class Camera {
        private int id;
        private String sourceDataUrl;
        private  String tokenDataUrl;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceDataUrl() {
        return sourceDataUrl;
    }

    public void setSourceDataUrl(String sourceDataUrl) {
        this.sourceDataUrl = sourceDataUrl;
    }

    public String getTokenDataUrl() {
        return tokenDataUrl;
    }

    public void setTokenDataUrl(String tokenDataUrl) {
        this.tokenDataUrl = tokenDataUrl;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", sourceDataUrl='" + sourceDataUrl + '\'' +
                ", tokenDataUrl='" + tokenDataUrl + '\'' +
                '}';
    }
}
