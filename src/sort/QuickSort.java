package sort;

public class QuickSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int i = start;
        int j = end - 1;
        int pivotIndex = getPivotIndex(arr, start, end);
        int key = arr[pivotIndex];

        while (i < j) {
            // 从前往后找到第一个大于key的
            while (arr[i] <= key) {
                if (++i == end) {
                    break;
                }
            }
            // 从后往前找到第一个小于key的
            while (arr[j] >= key) {
                if (--j == start) {
                    break;
                }
            }

            // i大于j，不用交换了
            if (i >= j) {
                break;
            } else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // key放到j的位置，此时key的最终位置已确定
        int temp = arr[j];
        arr[j] = key;
        arr[pivotIndex] = temp;

        // 递归地排序key两边的子数组
        sort(arr, start, j);
        sort(arr, j + 1, end);
    }

    private int getPivotIndex(int[] arr, int start, int end) {
        return start;
    }
}
