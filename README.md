# DAA Assignment 2 — Data Structures

Implementation and performance analysis of three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

The project includes JUnit 5 correctness tests, operation counters,
reproducible benchmarks, CSV results and performance plots.

---

## Project Structure

```text
DAA-Assignment2/
│
├── pom.xml
├── README.md
├── REPORT.md
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── structures/
│   │       │   ├── DynamicArray.java
│   │       │   ├── MyLinkedList.java
│   │       │   └── MinHeap.java
│   │       │
│   │       ├── metrics/
│   │       │   └── OperationCounter.java
│   │       │
│   │       └── benchmark/
│   │           └── BenchmarkRunner.java
│   │
│   └── test/
│       └── java/
│           ├── structures/
│           │   ├── DynamicArrayTest.java
│           │   ├── MyLinkedListTest.java
│           │   ├── MinHeapTest.java
│           │   └── OperationCounterTest.java
│           │
│           └── benchmark/
│
└── results/
    ├── results.csv
    ├── plot_results.py
    └── plots/
        ├── w1_time.png
        ├── w1_steps.png
        ├── w2_time.png
        ├── w2_operations.png
        ├── w3_time.png
        ├── w3_operations.png
        ├── w4_time.png
        └── w4_operations.png