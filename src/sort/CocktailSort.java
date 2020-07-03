package sort;

public class CocktailSort implements Sort {
    @Override
    public void sort(int[] arr, int start, int end) {
        for (int i = start; i < end / 2; i++) {
            // 正向，把最大的冒泡到最后
            for (int j = start; j < end - 1 - start; j++) {
                if (arr[j] > arr[j + 1]) {
                    int elem = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = elem;
                }
            }
            // 反向，把最小的冒泡到最前
            // 这里减去i + 1，是因为前面正向已经冒泡了一个最大的到数组最后了，可跳过
            for (int j = end - 1 - (i + 1); j > start; j--) {
                if (arr[j] < arr[j - 1]) {
                    int elem = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = elem;
                }
            }
        }
    }
}
