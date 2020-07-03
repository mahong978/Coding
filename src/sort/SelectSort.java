package sort;

public class SelectSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        for (int i = start; i < end; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < end; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
