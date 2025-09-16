import java.util.Random;

public class DataGenerator {
    public static int[] generateRandomArray(int size, int maxValue) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(maxValue) + 1; // values from 1 to maxValue
        }
        return arr;
    }
}