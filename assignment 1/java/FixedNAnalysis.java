import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FixedNAnalysis {

    private static final int[] N_VALUES = {1_000, 10_000, 100_000, 1_000_000,10_000_000}; // input sizes
    private static final int[] S_VALUES = {0, 10, 25, 32, 50, 75, 120, 145, 160, 185, 200, 300}; // thresholds
    private static final String OUTPUT_FILE = "partC_ii.csv";

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Dataset Size,S Value,Comparisons,Time (ms)\n");

            for (int N : N_VALUES) {
                System.out.println("Generating dataset of size N = " + N);
                int[] baseDataset = DataGenerator.generateRandomArray(N, N); // random values in [1..N]

                for (int S : S_VALUES) {
                    System.out.println("Running HybridSort with N = " + N + " and S = " + S);

                    int[] datasetCopy = baseDataset.clone();

                    // Reset comparisons
                    HybridSort.setComparisons(0);

                    // Measure time
                    long startTime = System.nanoTime();
                    HybridSort.hybridSort(datasetCopy, 0, datasetCopy.length - 1, S);
                    long endTime = System.nanoTime();

                    long elapsedMs = (endTime - startTime) / 1_000_000;
                    long comparisons = HybridSort.getComparisons();

                    // Write results to CSV
                    writer.write(String.format("%d,%d,%d,%d%n", N, S, comparisons, elapsedMs));

                    // Print results to console
                    System.out.printf("N=%d, S=%d, Comparisons=%d, Time=%dms%n", N, S, comparisons, elapsedMs);
                }
            }

            System.out.println("All results written to " + OUTPUT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

