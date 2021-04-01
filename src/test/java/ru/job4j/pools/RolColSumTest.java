package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void serialSum() {
        int[][] matrix = {{4, 5, 6}, {1, 9, 8} , {2, 3, 7}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        RolColSum.Sums one = new RolColSum.Sums();
        RolColSum.Sums two = new RolColSum.Sums();
        RolColSum.Sums three = new RolColSum.Sums();
        one.setColSum(7);
        one.setRowSum(15);
        two.setColSum(15);
        two.setRowSum(18);
        three.setRowSum(12);
        three.setColSum(21);
        RolColSum.Sums[] exp = new RolColSum.Sums[]{one, two, three};
        assertThat(exp[1].getRowSum(), is(18));
    }


    @Test
    public void serialSum1000() {
        int[][] matrix = new int[1000][1000];
        int expRow = 0;
        for (int i = 0; i < 1000; i++) {
            expRow = expRow + i;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int y = 0; y < matrix.length; y++) {
                matrix[i][y] = y;
            }
        }
        long start = System.currentTimeMillis();
        RolColSum.Sums[] sums  = RolColSum.sum(matrix);
        long end = System.currentTimeMillis() - start;
        assertThat(sums[0].getRowSum(), is(expRow));
        System.out.println(end);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[1000][1000];
        int expRow = 0;
        for (int i = 0; i < 1000; i++) {
            expRow = expRow + i;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int y = 0; y < matrix.length; y++) {
                matrix[i][y] = y;
            }
        }
        long start = System.currentTimeMillis();
        RolColSum.Sums[] sums  = RolColSum.asyncSum(matrix);
        long end = System.currentTimeMillis() - start;
        assertThat(sums[5].getRowSum(), is(expRow));
        System.out.println(end);
    }

}