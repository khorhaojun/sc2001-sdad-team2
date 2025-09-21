public class InsertionSort {

    private static long comparisons=0;

    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            for (int j = i; j > left; j--) {
                comparisons++;
                if (arr[j] < arr[j - 1])
                    swap(arr, j, j - 1);
                else
                    break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void setComparisons(long comp) {
		comparisons=comp;
	}

    public static long getComparisons() {
        return comparisons;
    }

}
