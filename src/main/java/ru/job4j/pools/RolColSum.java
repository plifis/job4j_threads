package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int y = 0; y < matrix[i].length; y++) {
                if (sums[y] == null) {
                    sums[y] = new Sums();
                }
                sum = sums[i].getRowSum();
                sums[i].setRowSum(sum + matrix[i][y]);
                sum = sums[y].getColSum();
                sums[y].setColSum(sum + matrix[i][y]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            int row = getSumRow(matrix, i).get();
            int col  = getSumCol(matrix, i).get();
            sums[i] = new Sums();
            sums[i].setRowSum(row);
            sums[i].setColSum(col);
        }
        return sums;
    }

    private static CompletableFuture<Integer> getSumRow(int[][] matrix, int i) {
      return CompletableFuture.supplyAsync(
              () -> {
                  int sum = 0;
                  for (int y = 0; y < matrix.length; y++) {
                        sum  = sum + matrix[i][y];
                    }
                    return sum;
              }
      );
    }

    private static CompletableFuture<Integer> getSumCol(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sum = 0;
                    for (int y = 0; y < matrix.length; y++) {
                        sum = sum + matrix[y][i];
                    }
                    return sum;
                }
        );
    }
}