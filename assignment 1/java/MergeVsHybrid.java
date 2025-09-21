import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MergeVsHybrid {

    private static final int N_VALUE = 10_000_000; // dataset size
    private static final int[] S_VALUES = {50, 300}; // thresholds for HybridSort
    private static final String OUTPUT_FILE = "merge_vs_hybrid.csv";

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Algorithm,Dataset Size,S Value,Comparisons,Time (ms)\n");

            System.out.println("Generating dataset of size N = " + N_VALUE);
            int[] baseDataset = DataGenerator.generateRandomArray(N_VALUE, N_VALUE);

            // --- MergeSort ---
            int[] mergeDataset = baseDataset.clone();
            MergeSort.setComparisons(0);

            long startTime = System.nanoTime();
            MergeSort.mergeSort(mergeDataset, 0, mergeDataset.length - 1);
            long endTime = System.nanoTime();

            long elapsedMs = (endTime - startTime) / 1_000_000;
            long comparisons = MergeSort.getComparisons();

            writer.write(String.format("MergeSort,%d,,%d,%d%n", N_VALUE, comparisons, elapsedMs));
            System.out.printf("MergeSort N=%d, Comparisons=%d, Time=%dms%n", N_VALUE, comparisons, elapsedMs);

            // --- HybridSort ---
            for (int S : S_VALUES) {
                int[] hybridDataset = baseDataset.clone();
                HybridSort.setComparisons(0);

                startTime = System.nanoTime();
                HybridSort.hybridSort(hybridDataset, 0, hybridDataset.length - 1, S);
                endTime = System.nanoTime();

                elapsedMs = (endTime - startTime) / 1_000_000;
                comparisons = HybridSort.getComparisons();

                writer.write(String.format("HybridSort,%d,%d,%d,%d%n", N_VALUE, S, comparisons, elapsedMs));
                System.out.printf("HybridSort N=%d, S=%d, Comparisons=%d, Time=%dms%n", N_VALUE, S, comparisons, elapsedMs);
            }

            System.out.println("Results written to " + OUTPUT_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


