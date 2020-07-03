package sort;

public class MergeSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        int[] tempArr = new int[arr.length];
        mergeSortNonRecursively(arr, tempArr, start, end);
    }

    private void mergeSort(int[] arr, int[] tempArr, int start, int end) {
        if (start >= end - 1) {
            return;
        }

        // 防止溢出
        int mid = start + (end - start) / 2;
        mergeSort(arr, tempArr, start, mid);
        mergeSort(arr, tempArr, mid, end);
        merge(arr, tempArr, start, mid, end);
    }

    private void mergeSortNonRecursively(int[] arr, int[] tempArr, int start, int end) {
        for (int step = 1, len = end - start; step < len; step *= 2) {
            for (int i = 0; i < len; i += step * 2) {
                int right = i + step + step;
                if (right > len) {
                    right = len;
                }
                merge(arr, tempArr, i, i + step, right);
            }
        }
    }

    private void merge(int[] arr, int[] tempArr, int start, int mid, int end) {
        int m = 0;
        int i = start;
        int j = mid;
        while (i < mid && j < end) {
            tempArr[m++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        while (i < mid) {
            tempArr[m++] = arr[i++];
        }
        while (j < end) {
            tempArr[m++] = arr[j++];
        }
        System.arraycopy(tempArr, 0, arr, start, end - start);
    }
}
