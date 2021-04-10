package ru.job4j.exam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.URL;
import java.util.concurrent.*;

public class CameraAggregator implements Aggregator{
    private final ForkJoinPool pool;
    private final String hyperLink;
    private final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    public CameraAggregator(String link) {
        this.hyperLink = link;
        this.pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    }
    @Override
    public void aggregate() {
        try {
            for (Camera camera : getCameraFromJSON(getSourceData(hyperLink).get())) {
                String sourceData = getSourceData(camera.getSourceDataUrl()).get();
                String tokenData = getSourceData(camera.getTokenDataUrl()).get();
                int id = camera.getId();
                String json = merge(id, sourceData, tokenData);
                this.add(json);
            }
        } catch(Exception e){
                e.printStackTrace();
            }
    }

    private synchronized void add(String json) {
        list.add(json);
    }


    private Camera[] getCameraFromJSON(String json) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(json, Camera[].class);
    }
    private CameraFull getCameraFullFromJSON(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, CameraFull.class);
    }

    private CompletableFuture<String> getSourceData(String link) {
        return CompletableFuture.supplyAsync(
                () -> {
                    StringBuilder builder = new StringBuilder();
                    try {
                        URL url = new URL(link);
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                            String str;
                            while ((str = in.readLine()) != null) {
                                    builder.append(str.trim());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return builder.toString();
                }, pool
        );
    }


    private String merge(int id, String sourceData, String tokenData) {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append(System.lineSeparator()).append("\"id\":").append(id).append(",").append(System.lineSeparator())
                .append(sourceData.replace("{", "").replace("}", "")).append(",").
                append(System.lineSeparator())
                .append(tokenData.replace("{", "").replace("}", ""))
                .append(System.lineSeparator())
                .append("}");
        return builder.toString();
    }


    public static void main(String[] args) {
        String link = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";
        CameraAggregator aggregator = new CameraAggregator(link);
        aggregator.aggregate();
       for (String s : aggregator.list) {
           System.out.println(s);
       }
    }
}


