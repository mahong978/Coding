package sort;

import com.sun.scenario.effect.Merge;

import java.util.Arrays;

public class SortTester {

    public static void main(String[] args) {
        Sort sort = new HeapSort();
        int[] arr = new int[] { 9, 8, 1, 4, 5, 100 };
        sort.sort(arr);

        System.out.println(Arrays.toString(arr));
    }

}
