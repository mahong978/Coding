package sort;

public class InsertSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        for (int i = start + 1; i < end; i++) {
            int value = arr[i - 1];
            for (int j = i - 1; j >= start; j--) {
                if (arr[j] > value) {
                    arr[j + 1] = arr[j];
                }
                arr[j + 1] = value;
            }
        }
    }
}
