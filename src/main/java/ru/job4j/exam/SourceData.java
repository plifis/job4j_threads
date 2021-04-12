package ru.job4j.exam;

public class SourceData {
    private String urlType;
    private String videoUrl;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "urlType='" + urlType + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
