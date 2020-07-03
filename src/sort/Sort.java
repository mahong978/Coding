package sort;

public interface Sort {

    default void sort(int[] arr) {
        sort(arr, 0, arr.length);
    }

    void sort(int[] arr, int start, int end);

}
