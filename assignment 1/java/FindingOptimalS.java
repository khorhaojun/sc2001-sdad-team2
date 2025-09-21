import java.io.FileWriter;
import java.io.IOException;

public class FindingOptimalS {
        public static void main(String[] args) {
        int maxN = 700;   // test dataset sizes up to 500
        int trials = 100;  // average over 50 runs
        int maxValue = 700;

        try (FileWriter writer = new FileWriter("crossover_results.csv")) {
            writer.write("Dataset Size,InsertionSort Time (ns),InsertionSort Comparisons,MergeSort Time (ns),MergeSort Comparisons\n");

            for (int n = 2; n <= maxN; n+=1) {
                long insertionTime = 0, mergeTime = 0;
                long insertionComps = 0, mergeComps = 0;

                for (int t = 0; t < trials; t++) {
                    int[] arr1 = DataGenerator.generateRandomArray(n, maxValue);
                    int[] arr2 = arr1.clone();

                    // InsertionSort
                    InsertionSort.setComparisons(0);
                    long t0 = System.nanoTime();
                    InsertionSort.insertionSort(arr1, 0, arr1.length - 1);
                    long t1 = System.nanoTime();
                    insertionTime += (t1 - t0);
                    insertionComps += InsertionSort.getComparisons();

                    // MergeSort
                    MergeSort.setComparisons(0);
                    long t2 = System.nanoTime();
                    MergeSort.mergeSort(arr2, 0, arr2.length - 1);
                    long t3 = System.nanoTime();
                    mergeTime += (t3 - t2);
                    mergeComps += MergeSort.getComparisons();
                }

                // Average over trials
                insertionTime /= trials;
                mergeTime /= trials;
                insertionComps /= trials;
                mergeComps /= trials;

                writer.write(String.format("%d,%d,%d,%d,%d\n",
                        n, insertionTime, insertionComps, mergeTime, mergeComps));
            }

            System.out.println("✅ Results saved to crossover_results.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
