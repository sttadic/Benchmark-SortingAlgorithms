public class SortingAlgorithmsBenchmark {
    
    // Method that copies source array
    public int[] copyArr(int[] src) {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    // Method that creates size n array of randomly generated numbers
    public int[] randomArr(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }

   
    public double benchmark(int n, String sort) {
        // Initialize totalTime variable
        double totalTime = 0;
        // Store randomly generated array of size n into an arr variable
        int[] arr = randomArr(n);

        // Iterate over 10 times (10 benchmarks for specific input)
        for (int i = 0; i < 10; i++) {

            // Store the copy of the original array into an arrCopy so sorting algorithm will always work on unsorted array
            int[] arrCopy = copyArr(arr);
            // Initialize startTime variable that holds time (since epoch) in nanoseconds
            long startTime = System.nanoTime();

            // Invoke sorting algorithm method based on 'sort' parameter
            switch (sort) {
                case "BubbleSort"     -> bubbleSort(arrCopy);
                case "SelectionSort"  -> selectionSort(arrCopy);
                case "InsertionSort"  -> insertionSort(arrCopy);                 
                case "QuickSort"      -> quickSort(arrCopy);
                case "RadixSort"      -> radixSort(arrCopy);
                default               -> System.exit(1);
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
        // Store length of an array into variable n
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
        // Iterate over an entire array (exclude the last element)
        for (int i = 0; i < arr.length - 1; i++) {
            // Assume that smallest element is at index 'i'
            int smallestAtIndex = i;
            // Iterate over the array starting from next element (i + 1)
            for (int j = i + 1; j < arr.length; j++) {
                // If smaller value is found, store it in smallestAtIndex variable
                if (arr[j] < arr[smallestAtIndex]) {
                    smallestAtIndex = j;
                }
            }
            // If smallestAtIndex has changed (meaning smaller emelent has been found), swap the values
            if (smallestAtIndex != i) {
                int temp = arr[i];
                arr[i] = arr[smallestAtIndex];
                arr[smallestAtIndex] = temp;
            }
        }
    }


    public void insertionSort(int[] arr) {
        
    }

    public void quickSort(int[] arr) {
  
    }

    public void radixSort(int[] arr) {
  
    }
    
    
    
    public static void main(String[] args) {
        SortingAlgorithmsBenchmark b = new SortingAlgorithmsBenchmark();

        // Array of input sizes
        int[] sizeArray = {100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};
        // Array of strings that represent sorting algorithms
        String[] algArray = {"BubbleSort", "SelectionSort", "InsertionSort", "QuickSort", "RadixSort"};

        // Print string "SIZE" and each input size on one line
        // Explanation on how to format with printf() method found at: https://www.baeldung.com/java-printstream-printf
        System.out.printf("%-20s", "SIZE");
        for (int i = 0; i < sizeArray.length; i++) {
            System.out.printf("%-10s", sizeArray[i]);
        }

        // For each sorting algorithm (string) iterate over an array of input sizes and pass these in as parameters to the benchmark() method
        for (String alg : algArray) {
            System.out.printf("%n%-20s", alg);
            
            for (int n : sizeArray) {
                System.out.printf("%-10.3f", b.benchmark(n, alg));
            }
        }
        System.out.println();
    }

}