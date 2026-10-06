package benchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

import structures.DynamicArray;
import structures.MinHeap;
import structures.MyLinkedList;

public class BenchmarkRunner {

    private static final int[] SIZES = {
            100, 1_000, 10_000, 100_000
    };

    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;

    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) throws IOException {

        File resultsDirectory = new File("results");

        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        File output = new File(
                resultsDirectory,
                "results.csv"
        );

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(output))) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            runW1(writer);
            runW2(writer);
            runW3(writer);
            runW4(writer);
        }

        System.out.println(
                "Benchmark complete: results/results.csv"
        );
    }

    // ---------------------------------------------------------
    // W1 - Random Access
    // ---------------------------------------------------------

    private static void runW1(PrintWriter writer) {

        System.out.println("Running W1 - Random Access");

        for (int n : SIZES) {

            int[] indexes = generateIndexes(n, 10_000);

            BenchmarkResult arrayResult =
                    benchmarkW1Array(n, indexes);

            writeResult(
                    writer,
                    "W1",
                    "-",
                    "DynamicArray",
                    n,
                    arrayResult
            );

            BenchmarkResult listResult =
                    benchmarkW1List(n, indexes);

            writeResult(
                    writer,
                    "W1",
                    "-",
                    "MyLinkedList",
                    n,
                    listResult
            );
        }
    }

    private static BenchmarkResult benchmarkW1Array(
            int n,
            int[] indexes) {

        DynamicArray array = buildArray(n);

        for (int i = 0; i < WARMUP_RUNS; i++) {

            for (int index : indexes) {
                array.get(index);
            }

            array.resetCounter();
        }

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS; run++) {

            array.resetCounter();

            long start = System.nanoTime();

            for (int index : indexes) {
                array.get(index);
            }

            long end = System.nanoTime();

            times[run] = end - start;

            steps += array.getCounter().getSteps();
            moves += array.getCounter().getMoves();
            comparisons += array.getCounter().getComparisons();
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    private static BenchmarkResult benchmarkW1List(
            int n,
            int[] indexes) {

        MyLinkedList list = buildList(n);

        for (int i = 0; i < WARMUP_RUNS; i++) {

            for (int index : indexes) {
                list.get(index);
            }

            list.resetCounter();
        }

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS; run++) {

            list.resetCounter();

            long start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            long end = System.nanoTime();

            times[run] = end - start;

            steps += list.getCounter().getSteps();
            moves += list.getCounter().getMoves();
            comparisons += list.getCounter().getComparisons();
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    // ---------------------------------------------------------
    // W2 - Search
    // ---------------------------------------------------------

    private static void runW2(PrintWriter writer) {

        System.out.println("Running W2 - Search");

        for (int n : SIZES) {

            int[] present = new int[500];
            int[] missing = new int[500];

            for (int i = 0; i < 500; i++) {

                present[i] = RANDOM.nextInt(n);

                missing[i] = n + 1 + i;
            }

            int[] queries = new int[1_000];

            System.arraycopy(
                    present, 0,
                    queries, 0,
                    500
            );

            System.arraycopy(
                    missing, 0,
                    queries, 500,
                    500
            );

            BenchmarkResult arrayResult =
                    benchmarkW2Array(n, queries);

            writeResult(
                    writer,
                    "W2",
                    "-",
                    "DynamicArray",
                    n,
                    arrayResult
            );

            BenchmarkResult listResult =
                    benchmarkW2List(n, queries);

            writeResult(
                    writer,
                    "W2",
                    "-",
                    "MyLinkedList",
                    n,
                    listResult
            );
        }
    }

    private static BenchmarkResult benchmarkW2Array(
            int n,
            int[] queries) {

        DynamicArray array = buildArray(n);

        warmupContainsArray(array, queries);

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS; run++) {

            array.resetCounter();

            long start = System.nanoTime();

            for (int value : queries) {
                array.contains(value);
            }

            long end = System.nanoTime();

            times[run] = end - start;

            steps += array.getCounter().getSteps();
            moves += array.getCounter().getMoves();
            comparisons += array.getCounter().getComparisons();
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    private static BenchmarkResult benchmarkW2List(
            int n,
            int[] queries) {

        MyLinkedList list = buildList(n);

        warmupContainsList(list, queries);

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS; run++) {

            list.resetCounter();

            long start = System.nanoTime();

            for (int value : queries) {
                list.contains(value);
            }

            long end = System.nanoTime();

            times[run] = end - start;

            steps += list.getCounter().getSteps();
            moves += list.getCounter().getMoves();
            comparisons += list.getCounter().getComparisons();
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    // ---------------------------------------------------------
    // W3 - Insert / Remove
    // ---------------------------------------------------------

    private static void runW3(PrintWriter writer) {

        System.out.println("Running W3 - Insert / Remove");

        for (int n : SIZES) {

            runW3Variant(
                    writer,
                    n,
                    "head",
                    0
            );

            runW3Variant(
                    writer,
                    n,
                    "middle",
                    n / 2
            );
        }
    }

    private static void runW3Variant(
            PrintWriter writer,
            int n,
            String variant,
            int index) {

        BenchmarkResult arrayResult =
                benchmarkW3Array(n, index);

        writeResult(
                writer,
                "W3",
                variant,
                "DynamicArray",
                n,
                arrayResult
        );

        BenchmarkResult listResult =
                benchmarkW3List(n, index);

        writeResult(
                writer,
                "W3",
                variant,
                "MyLinkedList",
                n,
                listResult
        );
    }

    private static BenchmarkResult benchmarkW3Array(
            int n,
            int index) {

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS + WARMUP_RUNS; run++) {

            DynamicArray array = buildArray(n);

            array.resetCounter();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {

                int insertIndex = Math.min(
                        index,
                        array.size()
                );

                array.add(insertIndex, i);
            }

            for (int i = 0; i < 1_000; i++) {

                int removeIndex = Math.min(
                        index,
                        array.size() - 1
                );

                array.remove(removeIndex);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {

                int measuredRun = run - WARMUP_RUNS;

                times[measuredRun] = end - start;

                steps += array.getCounter().getSteps();
                moves += array.getCounter().getMoves();
                comparisons += array.getCounter().getComparisons();
            }
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    private static BenchmarkResult benchmarkW3List(
            int n,
            int index) {

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < MEASURED_RUNS + WARMUP_RUNS; run++) {

            MyLinkedList list = buildList(n);

            list.resetCounter();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {

                int insertIndex = Math.min(
                        index,
                        list.size()
                );

                list.add(insertIndex, i);
            }

            for (int i = 0; i < 1_000; i++) {

                int removeIndex = Math.min(
                        index,
                        list.size() - 1
                );

                list.remove(removeIndex);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {

                int measuredRun = run - WARMUP_RUNS;

                times[measuredRun] = end - start;

                steps += list.getCounter().getSteps();
                moves += list.getCounter().getMoves();
                comparisons += list.getCounter().getComparisons();
            }
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    // ---------------------------------------------------------
    // W4 - Priority Processing
    // ---------------------------------------------------------

    private static void runW4(PrintWriter writer) {

        System.out.println("Running W4 - Priority Processing");

        for (int n : SIZES) {

            BenchmarkResult result =
                    benchmarkW4(n);

            writeResult(
                    writer,
                    "W4",
                    "-",
                    "MinHeap",
                    n,
                    result
            );
        }
    }

    private static BenchmarkResult benchmarkW4(int n) {

        long[] times = new long[MEASURED_RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0;
             run < MEASURED_RUNS + WARMUP_RUNS;
             run++) {

            MinHeap heap = new MinHeap();

            int[] values = new int[n];

            Random random = new Random(42);

            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt();
            }

            heap.resetCounter();

            long start = System.nanoTime();

            for (int value : values) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "MinHeap output is not non-decreasing"
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {

                int measuredRun = run - WARMUP_RUNS;

                times[measuredRun] = end - start;

                steps += heap.getCounter().getSteps();
                moves += heap.getCounter().getMoves();
                comparisons += heap.getCounter().getComparisons();
            }
        }

        return createResult(
                times,
                steps / MEASURED_RUNS,
                moves / MEASURED_RUNS,
                comparisons / MEASURED_RUNS
        );
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private static DynamicArray buildArray(int n) {

        DynamicArray array = new DynamicArray(n);

        for (int i = 0; i < n; i++) {
            array.add(i);
        }

        array.resetCounter();

        return array;
    }

    private static MyLinkedList buildList(int n) {

        MyLinkedList list = new MyLinkedList();

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        list.resetCounter();

        return list;
    }

    private static int[] generateIndexes(
            int n,
            int count) {

        Random random = new Random(42);

        int[] indexes = new int[count];

        for (int i = 0; i < count; i++) {
            indexes[i] = random.nextInt(n);
        }

        return indexes;
    }

    private static void warmupContainsArray(
            DynamicArray array,
            int[] queries) {

        for (int value : queries) {
            array.contains(value);
        }

        array.resetCounter();
    }

    private static void warmupContainsList(
            MyLinkedList list,
            int[] queries) {

        for (int value : queries) {
            list.contains(value);
        }

        list.resetCounter();
    }

    private static BenchmarkResult createResult(
            long[] times,
            long steps,
            long moves,
            long comparisons) {

        return new BenchmarkResult(
                median(times) / 1_000_000.0,
                steps,
                moves,
                comparisons
        );
    }

    private static double median(long[] values) {

        long[] sorted = values.clone();

        Arrays.sort(sorted);

        int middle = sorted.length / 2;

        if (sorted.length % 2 == 0) {

            return (
                    sorted[middle - 1]
                            + sorted[middle]
            ) / 2.0;

        } else {

            return sorted[middle];
        }
    }

    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            BenchmarkResult result) {

        writer.printf(
                "%s,%s,%s,%d,%.6f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                result.timeMs,
                result.steps,
                result.moves,
                result.comparisons
        );
    }

    private static class BenchmarkResult {

        double timeMs;
        long steps;
        long moves;
        long comparisons;

        BenchmarkResult(
                double timeMs,
                long steps,
                long moves,
                long comparisons) {

            this.timeMs = timeMs;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}