import java.io.FileWriter;
import java.io.IOException;

public class SortingAlgorithmsBenchmark {
    
    // Utility method that copies source array
    public int[] copyArr(int[] src) {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }


    // Utility method to create size n array of randomly generated numbers
    public int[] randomArr(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }

    
    // Utility and Benchmark methods are slightly modified versions of Dr. Dominic Carr's benchmark example. Source code:
    // https://vlegalwaymayo.atu.ie/pluginfile.php/1183960/mod_resource/content/0/Main.java
    public double benchmark(int n, String sort) {
        // Initialize totalTime variable
        double totalTime = 0;
        // Store randomly generated array of size n to arr variable
        int[] arr = randomArr(n);

        // Perform 10 benchmarks for each sorting algorithm
        for (int i = 0; i < 10; i++) {

            // Store the copy of the original array into an arrCopy so sorting algorithm always works on unsorted arrays
            int[] arrCopy = copyArr(arr);
            // Initialize startTime variable that holds time (since epoch) in nanoseconds
            long startTime = System.nanoTime();

            // Invoke sorting algorithm based on a 'sort' parameter. If method is not found, return NaN as an error indicator
            switch (sort) {
                case "BubbleSort"     -> bubbleSort(arrCopy);
                case "SelectionSort"  -> selectionSort(arrCopy);
                case "InsertionSort"  -> insertionSort(arrCopy);                 
                case "MergeSort"      -> mergeSort(arrCopy, 0, n-1);
                case "CountingSort"   -> countingSort(arrCopy, n);
                default               -> {
                    return Double.NaN;
                }
            }

            // Store time in nanoseconds after sorting is completed
            long endTime = System.nanoTime();
            // Calculate running time of sorting algorithm in milliseconds
            double elapsedTime = (endTime - startTime) / 1000000.0;
            // Add elapsedTime to totalTime for each iteration
            totalTime += elapsedTime;
        }   
        // Return the average running time of an algorithm
        return totalTime/10;
    }


    // BUBBLE SORT
    public void bubbleSort(int[] arr) {
        // Initialize isSorted to act as a flag to track whether swaps have been made in a pass through an array
        boolean isSorted = false;
        // Assign length of the array to a variable n
        int n = arr.length;

        while (!isSorted) {
            // Set flag to true - if no swaps have been made in a pass through the array (means sorted), flag will stay true and stop the loop
            isSorted = true;
            // Iterate over every element of an array starting from second
            for (int i = 1; i < n; i++) {
                // If element on the left is larger than one on the right -> swap and set isSorted flag to false
                if (arr[i-1] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[i-1];
                    arr[i-1] = temp;
                    isSorted = false;
                }
            }
            // Largest element is placed at the correct position, last index of the array, after each iteration.
            // Therefore, decrement n so algorithm would exclude already sorted part of the array on each subsequent pass 
            n--;
        }
    }


    // SELECTION SORT
    public void selectionSort(int[] arr) {
        // Iterate over an entire array excluding the last element
        for (int i = 0; i < arr.length - 1; i++) {
            // Assume the smallest value is at index 'i' and store it in a smallestAtIndex variable
            int smallestAtIndex = i;
            // Iterate over the array starting from next element (i + 1)
            for (int j = i + 1; j < arr.length; j++) {
                // If smaller value is found, assign its index to the smallestAtIndex variable
                if (arr[j] < arr[smallestAtIndex]) {
                    smallestAtIndex = j;
                }
            }
            // If smallestAtIndex has changed, which means that smaller value has been found, swap the values
            if (smallestAtIndex != i) {
                int temp = arr[i];
                arr[i] = arr[smallestAtIndex];
                arr[smallestAtIndex] = temp;
            }
        }
    }


    // INSERTION SORT 
    // Algoritham is implemented using psoudocode found at: https://www.baeldung.com/java-insertion-sort
    public void insertionSort(int[] arr) {
        // Iterate over entire array starting from the second element
        for (int i = 1; i < arr.length; i++) {
            // Assign current element's value to a variable key 
            int key = arr[i];
            // Store index of an element that comes before i (current element) in the variable j
            int j = i - 1;

            // Move elements that come before and are greater than the key to the right
            while (j >= 0 && arr[j] > key) {
                arr[j+1] = arr[j];
                // Step towards the start of the array so all elements to the left are compared with the key
                j--;
            }
            // Insert the key in front of the elements that have been moved to the right (elements that are larger than the key itself)
            arr[j+1] = key;
        }
    }


    // MERGE SORT helper method that sorts and merges subarrays
    // Source: https://www.geeksforgeeks.org/merge-sort/?ref=header_search
    public void merge(int[] arr, int left, int middle, int right) {
        
        // Calculate sizes of subarrays
        // Subbary arr[left to middle] - add 1 to include all indices starting form zero up to the middle point (inclusive)
        int n1 = middle - left + 1;
        // Subarray arr[middle+1 to right]
        int n2 = right - middle;

        // Initialize temporary (sub)arrays of appropriate sizes calculated above
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copy elements of array to temporary subarrays so they take only elements specified by left, middle or right indexes
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[middle + 1 + j];
        }

        // Initialize variables to track indexes of both temporary subarrays
        int i = 0;
        int j = 0;
        // Initialize variable to track indexes of array that was originally passed in to merge() method
        int k = left;

        // Keep iterating until either index reaches end of its respective subarray, compare values at given indexes of 
        // subarrays and insert smaller element into array at appropriate position 
        while (i < n1 && j < n2) {
            
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;       
        }

        // Insert any remaining elements from temporary subarrays to finish merging and sorting process
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }


    // MERGE SORT
    // Source: https://www.geeksforgeeks.org/merge-sort/?ref=header_search
    public void mergeSort(int[] arr, int left, int right) {
        
        // Base case - terminate recursive calls once left and right variables (indexes) are equal (only one element left)
        if (left < right) {

            // Calculate middle index in a way it prevents integer overflow
            int middle = left + (right - left) / 2;

            // Recursively call mergeSort() with smaller subarrays until base case is reached. After recursive call terminates,
            // return control to the previous call. After both recursive calls have completed (terminated), proceed to execute merge()
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            // Call the helper method to sort and merge subarrays
            merge(arr, left, middle, right);
        }
    }   


    // COUNTING SORT
    // Slightly modified version of algorithm found at: https://www.programiz.com/dsa/counting-sort
    public void countingSort(int[] arr, int n) {
        
        // Initialize output array
        int[] output = new int[n + 1];

        // Find the largest element of the input array
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i]);
        }

        // Initialize the array of max+1 size, and fill it with zeros so we can store frequency of each element
        int[] count = new int[max + 1];
        for (int i = 0; i < max; i++) {
            count[i] = 0;
        }

        // Store the count (frequency) of each element at their respective index in the 'count' array. Do this by incrementing the value
        // of an element of count array at particular index. That index is determined by the value of an element of original input array
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // Store the cumulative sum of the elements in the count array to determine number of elements that are less or equal to the
        // input elemets
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Iterate over the input array in the reverse order, and store the element of the input array at the particular index of
        // the output (sorted) array
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            // Decrement cumulative sum at index which is determined by the current element of the input array
            count[arr[i]]--;
        }

        // Copy elements from sorted output array into original array
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }   



    public void init(int[] sizeArr, String[] algArr, FileWriter writer) throws IOException {
        
        // Print string "SIZE" and each input size on one line by iterating over sizeArr, write to a CSV file
        // Explanation on how to format with printf() method found at: https://www.baeldung.com/java-printstream-printf
        System.out.printf("%-20s", "SIZE");
        writer.append("SIZE,");
        for (int i = 0; i < sizeArr.length; i++) {
            System.out.printf("%-10s", sizeArr[i]);
            // Store each input size into a CSV adding a new line only after a last element of the array
            if (i < sizeArr.length-1) {
                writer.append(sizeArr[i] + ",");
            } else writer.append(sizeArr[i] + "\n");
        }
   
        // For each sorting algorithm iterate over an array of input sizes and pass them in as parameters to the benchmark() method
        // Print sorting algorithm and its respective benchmark results appropriately formatted on one line
        for (String alg : algArr) {
            System.out.printf("%n%-20s", alg);
            // Write each algorithm name to a csv followed by a comma
            writer.append(alg + ",");
            
            for (int i = 0; i < sizeArr.length; i++) {
                String benchResult = String.format("%.3f", benchmark(sizeArr[i], alg));
                System.out.printf("%-10s", benchResult);

                // Write results of a each benchmark to a .csv file
                if (i < sizeArr.length-1) {
                    writer.append(benchResult + ",");
                } else writer.append(benchResult + "\n");
            }
        }
        System.out.println();
    }
    
    
    
    public static void main(String[] args) throws IOException {
        
        SortingAlgorithmsBenchmark bench = new SortingAlgorithmsBenchmark();
        // Instantiate FileWriter object and pass the name of csv file into its constructor
        FileWriter w = new FileWriter("benchmark_results.csv");
        
        // Declare an array of input sizes
        int[] sizeArray = {100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};
        // Array of strings that represent sorting algorithms
        String[] algArray = {"BubbleSort", "SelectionSort", "InsertionSort", "MergeSort", "CountingSort"};
        
        bench.init(sizeArray, algArray, w);

         // Write all the data from the buffer to the csv file
         w.flush();
         // Close FileWriter object
         w.close();
    }

}