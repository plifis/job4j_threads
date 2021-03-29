package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();


    /**
     * Добавляет объект в Хранилище
     * @param model объект класса Base, который необходимо доабвить
     * @return возвращает истину, если результат метода putIfAbsent() не равен null
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }


    /**
     * Обновление объекта в Хранилище
     * @param model объект, который требуется обновить
     * @return возвращает истину, если результат метода computeIfPresent() не равен null
     */
    public boolean update(Base model) {
       return memory.computeIfPresent(model.getId(), (k, v) -> {
           Base temp = memory.get(model.getId());
            if (temp.getVersion().get() != model.getVersion().get()) {
                throw new OptimisticException("Versions are not equal");
            }
            int version = model.getVersion().get();
            model.getVersion().set(version + 1);
            v = model;
            return v;
        }
        ) != null;
    }

    /**
     * Удаляет объект из Хранилища
     * @param model объект класса Base, который требуется удалить
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * Возвращает объект из Хранилища
     * @param id идентификатор объекта, который требуется вернуть. В Хранилище представлен как "ключ"
     * @return возвращает объект класса Base, либо null, если объект с данным идентификатором не найден
     */
    public Base getElement(int id) {
        return memory.get(id);
    }
}