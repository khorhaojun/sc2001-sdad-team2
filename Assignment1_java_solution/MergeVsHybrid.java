import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MergeVsHybrid {

    private static final int N_VALUE = 10_000_000; // dataset size
    private static final int[] S_VALUES = {32, 300}; // thresholds for HybridSort
    private static final int TRIALS = 10;           // number of experiments
    private static final String OUTPUT_FILE = "merge_vs_hybrid_avg.csv";

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Algorithm,Dataset Size,S Value,Avg Comparisons,Avg Time (ms)\n");

            System.out.println("Generating dataset of size N = " + N_VALUE);
            int[] baseDataset = DataGenerator.generateRandomArray(N_VALUE, N_VALUE);

            // --- MergeSort ---
            long totalComparisons = 0;
            long totalTime = 0;

            for (int t = 0; t < TRIALS; t++) {
                int[] mergeDataset = baseDataset.clone();
                MergeSort.setComparisons(0);

                long startTime = System.nanoTime();
                MergeSort.mergeSort(mergeDataset, 0, mergeDataset.length - 1);
                long endTime = System.nanoTime();

                totalTime += (endTime - startTime) / 1_000_000;
                totalComparisons += MergeSort.getComparisons();
            }

            long avgTime = totalTime / TRIALS;
            long avgComparisons = totalComparisons / TRIALS;

            writer.write(String.format("MergeSort,%d,,%d,%d%n", N_VALUE, avgComparisons, avgTime));
            System.out.printf("MergeSort N=%d, Avg Comparisons=%d, Avg Time=%dms%n",
                    N_VALUE, avgComparisons, avgTime);

            // --- HybridSort ---
            for (int S : S_VALUES) {
                totalComparisons = 0;
                totalTime = 0;

                for (int t = 0; t < TRIALS; t++) {
                    int[] hybridDataset = baseDataset.clone();
                    HybridSort.setComparisons(0);

                    long startTime = System.nanoTime();
                    HybridSort.hybridSort(hybridDataset, 0, hybridDataset.length - 1, S);
                    long endTime = System.nanoTime();

                    totalTime += (endTime - startTime) / 1_000_000;
                    totalComparisons += HybridSort.getComparisons();
                }

                avgTime = totalTime / TRIALS;
                avgComparisons = totalComparisons / TRIALS;

                writer.write(String.format("HybridSort,%d,%d,%d,%d%n", N_VALUE, S, avgComparisons, avgTime));
                System.out.printf("HybridSort N=%d, S=%d, Avg Comparisons=%d, Avg Time=%dms%n",
                        N_VALUE, S, avgComparisons, avgTime);
            }

            System.out.println("Averaged results written to " + OUTPUT_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
