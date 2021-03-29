package ru.job4j.pools;

import ru.job4j.cache.OptimisticException;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final ForkJoinPool pool;
    private final T target;
     private static final int THRESHOLD = 10;


    public ParallelIndexSearch(T[] array, T target) {
        this.array = array;
        this.target = target;
        pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    }


    @Override
    protected Integer compute() {
        if (array.length < THRESHOLD) {
            return this.search(array, target);
        } else {
            int mid = array.length / 2;
            T[] leftArr = Arrays.copyOfRange(array, 0, mid);
            T[] rightArr = Arrays.copyOfRange(array, mid + 1, array.length - 1);
           ParallelIndexSearch<T> left = new ParallelIndexSearch<T>(leftArr, target);
           ParallelIndexSearch<T> right = new ParallelIndexSearch<T>(rightArr, target);
           ForkJoinTask.invokeAll(left, right);
           int leftRes = left.join();
           int rightRes = right.join();
           return this.merge(leftRes, rightRes);
        }
    }

    /**
     * Ищет совпадение в массиве с искомым занчением и возращает индекс элемента в массиве
     * @param array массив в котором осущестялется поиск
     * @param target искомый элемент (значение)
     * @return и
     */
    private Integer search(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Возвращаем первый найденный индекс
     * @param left индекс левого массива, если значения не было найдено -1
     * @param right индекс правого массива, если значения не было найдено -1
     * @return возвращаем первый найденный индекс
     */
    private Integer merge(int left, int right) {
        int index;
        if (left > -1) {
            index = left;
        } else if (right > -1) {
            index = right;
        } else {
            throw new IllegalArgumentException("Искомое значение не найдено");
        }
        return index;
    }
}
