import java.io.FileWriter;
import java.io.IOException;

public class FindingOptimalS {

    public static void main(String[] args) {
        int maxN = 700;        
        int trials = 200;       // number of trials for averaging
        int maxValue = 700;    

        try (FileWriter writer = new FileWriter("average_results.csv")) {
            writer.write("Dataset Size,InsertionSort Avg Time (ns),MergeSort Avg Time (ns)\n");

            int bestS = -1;

            for (int n = 2; n <= maxN; n++) {
                long totalInsertionTime = 0;
                long totalMergeTime = 0;

                for (int t = 0; t < trials; t++) {
                    int[] arr1 = DataGenerator.generateRandomArray(n, maxValue);
                    int[] arr2 = arr1.clone();

                    // InsertionSort
                    InsertionSort.setComparisons(0);
                    long start1 = System.nanoTime();
                    InsertionSort.insertionSort(arr1, 0, arr1.length - 1);
                    long end1 = System.nanoTime();
                    totalInsertionTime += (end1 - start1);

                    // MergeSort
                    MergeSort.setComparisons(0);
                    long start2 = System.nanoTime();
                    MergeSort.mergeSort(arr2, 0, arr2.length - 1);
                    long end2 = System.nanoTime();
                    totalMergeTime += (end2 - start2);
                }

                long avgInsertionTime = totalInsertionTime / trials;
                long avgMergeTime = totalMergeTime / trials;

                writer.write(String.format("%d,%d,%d\n", n, avgInsertionTime, avgMergeTime));

                // Improved decision: when InsertionSort average time exceeds MergeSort,
                // take the previous N as the optimal S
                if (avgInsertionTime > avgMergeTime && bestS == -1 && n > 10) {
                    bestS = n - 1; // Take the previous N
                }
            }

            // If InsertionSort never exceeded MergeSort, take the maximum N
            if (bestS == -1) bestS = maxN;

            System.out.println("Average results saved to average_results.csv");
            System.out.println("Optimal S (InsertionSort threshold) ≈ " + bestS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}