# Benchmark-SortingAlgorithms

## Sorting algorithms
- Bubble Sort
- Selection Sort
- Insertion Sort
- Merge Sort
- Counting Sort

## Description
The application is entirely implemented within a single class consisting of benchmark method, utility methods, and sorting algorithms. To automate the process of benchmarking, two arguments are passed into the benchmark method, one specifying the input size, and the other defining the actual sorting algorithm to be tested. Arrays of input sizes and sorting algorithms are declared within the main method.

The benchmark method, on each sorting algorithm, runs ten times for different input sizes. The current time is taken before and after each sorting algorithm executes, and then differences of those times are added and stored into a separate variable. Once all ten passes are completed, the benchmark method returns the average time in milliseconds, which is then outputted to the console and stored in a CSV file. The process is repeated for each input size and sorting algorithm.
