import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FixedNAnalysis {

    private static final int[] N_VALUES = {1_000, 10_000, 100_000, 1_000_000, 10_000_000}; // input sizes
    private static final int[] S_VALUES = {10, 15, 20, 25, 30, 32, 35, 40, 45, 50, 60, 75, 90, 100, 120, 140, 160, 180, 200, 250, 300}; // thresholds
    private static final String OUTPUT_FILE = "partC_ii.csv";
    private static final int TRIALS = 10;

    public static void main(String[] args) {
        Map<Integer, Integer> bestSForN = new HashMap<>();
        Map<Integer, Long> bestTimeForN = new HashMap<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Dataset Size,S Value,Avg Comparisons,Avg Time (ms)\n");

            for (int N : N_VALUES) {
                long minAvgTime = Long.MAX_VALUE;
                int bestS = -1;

                for (int S : S_VALUES) {
                    long totalComparisons = 0;
                    long totalTime = 0;

                    System.out.println("Running HybridSort with N = " + N + " and S = " + S);

                    for (int trial = 1; trial <= TRIALS; trial++) {
                        int[] dataset = DataGenerator.generateRandomArray(N, N);

                        HybridSort.setComparisons(0);

                        long startTime = System.nanoTime();
                        HybridSort.hybridSort(dataset, 0, dataset.length - 1, S);
                        long endTime = System.nanoTime();

                        long elapsedMs = (endTime - startTime) / 1_000_000;
                        long comparisons = HybridSort.getComparisons();

                        totalTime += elapsedMs;
                        totalComparisons += comparisons;

                        System.out.printf("Trial %d: N=%d, S=%d, Comparisons=%d, Time=%dms%n",
                                trial, N, S, comparisons, elapsedMs);
                    }

                    long avgTime = totalTime / TRIALS;
                    long avgComparisons = totalComparisons / TRIALS;

                    writer.write(String.format("%d,%d,%d,%d%n", N, S, avgComparisons, avgTime));

                    System.out.printf("N=%d, S=%d, Avg Comparisons=%d, Avg Time=%dms%n",
                            N, S, avgComparisons, avgTime);

                    if (avgTime < minAvgTime) {
                        minAvgTime = avgTime;
                        bestS = S;
                    }
                }

                bestSForN.put(N, bestS);
                bestTimeForN.put(N, minAvgTime);
            }

            System.out.println("All averaged results written to " + OUTPUT_FILE);

            System.out.println("\n=== Optimal S values for each N ===");
            for (int N : N_VALUES) {
                System.out.printf("N=%d -> Optimal S=%d (Avg Time=%dms)%n",
                        N, bestSForN.get(N), bestTimeForN.get(N));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}