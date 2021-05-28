package ru.job4j.exam;

import java.io.*;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Класс реализует алгоритм получения информации о видео камерах для доступа к которой пердаётся ссылка на общий список камер
 * каждая запись о камере так же содержит ссылку на общую информацию о камере и ссылку на информацию о токене доступа к камере
 * Вся информация представлена в формате JSON
 */

public class CameraAggregator implements Aggregator{
    private ExecutorService executor;
    private final String hyperLink;
    private final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    public CameraAggregator(String link) {
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.hyperLink = link;
    }

    @Override
    public void aggregate() {
        try {
            String json = getSource(hyperLink).get();
                this.add(json);
    } catch(Exception e){
                e.printStackTrace();
            }
    }

    private void add(String json) {
        list.add(json);
    }

    /**
     * Метод парсит строку содержащую адрес гипперсылки, идентификатор,
     * извлекает по ссылке на источник данных (SourceDataUrl) статус (Live etc) и ip адрес камеры
     * извлекает по ссылке на токен камеры (tokenDataUrl), значение ключа (value) и время жизни пакета (ttl)
     * @param link ссылка на список камер
     * @return Future, содержащий результат работы в виде объекта String
     */
    public Future<String> getSource(String link) {
        return this.getExecutor().submit(
                () -> {
                    StringBuilder builder = new StringBuilder();
                    try {
                        URL url = new URL(link);
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                            String str;
                            while ((str = in.readLine()) != null) {
                                if ((str.contains("sourceDataUrl")) || (str.contains("tokenDataUrl"))) {
                                    builder.append(getSource(getSubLink(str)).get());
                                } else {
                                    builder.append(str.trim());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return builder.toString();
                }
        );
    }

    public ExecutorService getExecutor() {
        return executor;
    }


    /**
     * Получение адреса гиперссылки из строки
     * @param link строка содержащая адрес гиперссылку
     * @return адрес гипперссылки
     */
    private String getSubLink(String link) {
        int endStr = link.lastIndexOf("\"");
        return link.substring(link.indexOf("http"), endStr);
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


