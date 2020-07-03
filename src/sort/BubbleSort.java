package sort;

public class BubbleSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            for (int j = start; j < end - 1 - i; j++) {
                int elem1 = arr[j];
                int elem2 = arr[j + 1];
                if (elem1 > elem2) {
                    arr[j] = elem2;
                    arr[j + 1] = elem1;
                }
            }
        }
    }
}
