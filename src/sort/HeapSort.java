package sort;

public class HeapSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        int len = end - start;
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(arr, i, end);
        }
        for (int i = end - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int start, int end) {
        int parent = start;
        int child = start * 2 + 1;

        while (child < end) {
            int child2 = child + 1;
            if (child2 < end && arr[child2] > arr[child]) {
                child = child2;
            }

            if (arr[child] < arr[parent]) {
                break;
            } else {
                int temp = arr[parent];
                arr[parent] = arr[child];
                arr[child] = temp;

                parent = child;
                child = parent * 2 + 1;
            }
        }
    }
}
