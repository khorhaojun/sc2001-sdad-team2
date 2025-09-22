import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FixedSAnalysis {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000, 10_000_000};
    private static final int S = 32;  // fixed threshold
    private static final String OUTPUT_FILE = "partC_i.csv";

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Dataset Size,S Value,Comparisons,Time (ms)\n");

            for (int n : SIZES) {
                System.out.println("Running HybridSort with n = " + n);

                int[] dataset = DataGenerator.generateRandomArray(n, n); // random numbers in [1..n]

                // Reset comparisons
                HybridSort.setComparisons(0);

                // Measure time
                long startTime = System.nanoTime();
                HybridSort.hybridSort(dataset, 0, dataset.length - 1, S);
                long endTime = System.nanoTime();

                long elapsedMs = (endTime - startTime) / 1_000_000;
                long comparisons = HybridSort.getComparisons();

                // Write result to CSV
                writer.write(String.format("%d,%d,%d,%d%n", n, S, comparisons, elapsedMs));

                // Print result to console
                System.out.printf("n=%d, S=%d, Comparisons=%d, Time=%dms%n", n, S, comparisons, elapsedMs);
            }

            System.out.println("Results written to " + OUTPUT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
