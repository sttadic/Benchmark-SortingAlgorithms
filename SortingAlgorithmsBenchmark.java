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

    // Utility and Benchmark methods are slightly modified versions from Dr. Dominic Carr's benchmark example. Source code at:
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
                case "MergeSort"      -> mergeSort(arrCopy, 0, arrCopy.length-1);
                case "BucketSort"     -> bucketSort(arrCopy, arrCopy.length);
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


    // Insertion sort algoritham is implemented using psoudocode found at: https://www.baeldung.com/java-insertion-sort
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



    // Helper method for the mergeSort() that sorts and merges subarrays. Origin: https://www.geeksforgeeks.org/merge-sort/?ref=header_search
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

    // Solution for MergeSort taken from: https://www.geeksforgeeks.org/merge-sort/?ref=header_search
    public void mergeSort(int[] arr, int left, int right) {
        
        // Base case - terminate recursive calls once left and right variables (indexes) are equal
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


    public void bucketSort(int[] arr, int n) {
  
    }
    
    
    
    public static void main(String[] args) {
        SortingAlgorithmsBenchmark b = new SortingAlgorithmsBenchmark();

        // Array of input sizes
        int[] sizeArray = {100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};
        // Array of strings that represent sorting algorithms
        String[] algArray = {"BubbleSort", "SelectionSort", "InsertionSort", "MergeSort", "BucketSort"};

        // Print string "SIZE" and each input size on one line
        // Explanation on how to format with printf() method found at: https://www.baeldung.com/java-printstream-printf
        System.out.printf("%-20s", "SIZE");
        for (int i = 0; i < sizeArray.length; i++) {
            System.out.printf("%-10s", sizeArray[i]);
        }

        // For each sorting algorithm (string) iterate over an array of input sizes and pass them in as parameters to the benchmark()
        for (String alg : algArray) {
            System.out.printf("%n%-20s", alg);
            
            for (int n : sizeArray) {
                System.out.printf("%-10.3f", b.benchmark(n, alg));
            }
        }
        System.out.println();
    }

}