package LE6Q1;
import java.util.*;


public class CaelanTestingSortingMethods {

    public static void main(String[]args) {
        myHeader("Caelan", 251283976, "Testing sorting methods");
        //header method


//        Integer[]bigArray = new Integer[5000];
//        Integer[]bigBackUpArray = new Integer[5000];
//        for (int i = 0; i < bigArray.length; i++) {
//            bigArray[i] = (int) (Math.random()*81) + 13;
//        }
//
//
//
//
//        for(int j = 0; j < bigArray.length; j++){
//            bigBackUpArray[j] = bigArray[j];
//        }
//        List<Integer> bigList = new ArrayList<>(Arrays.asList(bigArray));
//        long startTime = System.nanoTime();
//        Collections.sort(bigList);
//        long endTime = System.nanoTime();
//        long elapsedTime = (endTime - startTime);
//        System.out.printf("My Collections-Sort Time: %.2f milliseconds" , elapsedTime / Math.pow(10,6));
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Selection-Sort Time: %.2f milliseconds" ,  selectionSort(bigArray) / Math.pow(10,6) );
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Selection-Sort Time: %.2f milliseconds" ,  selectionSort(bigArray) / Math.pow(10,6) );
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Bubble-Sort Time: %.2f milliseconds" , bubbleSort(bigArray) / Math.pow(10,6));
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Insertion-Sort Time: %.2f milliseconds " ,  insertionSort(bigArray) / Math.pow(10,6));
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Merge-Sort Time: %.2f milliseconds" , mergeSort(bigArray) / Math.pow(10,6));
//        bigArray = bigBackUpArray.clone();
//        System.out.printf("\nMy Quick-Sort Time: %.2f milliseconds" , quickSort(bigArray,0, bigArray.length - 1) / Math.pow(10,6));
//        bigArray = bigBackUpArray.clone();
//        Integer[] bigBucketSortArray = Arrays.copyOf(bigBackUpArray, bigBackUpArray.length);
//        System.out.printf("\nMy Bucket-Sort Time: %.2f milliseconds", bucketSort(bigBucketSortArray, 0, bigBucketSortArray.length - 1, 2) / Math.pow(10, 6));







        System.out.println("Testing execution time of different sorting methods for sorting 5 random numbers: ");
        Integer[] array = new Integer[5];
        Integer[] backUpArray = new Integer[5];
        //creating an array and a backup array

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random()*81) + 13;
        }
        //populating the array with random numbers between 13 and 93 inclusive



        for(int j = 0; j < array.length; j++){
            backUpArray[j] = array[j];
        }

        //populating the backup array between 13 and 93 inclusive
        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        //creating an array list

        long startTime = System.nanoTime();
        Collections.sort(list);
        long endTime = System.nanoTime();
        //getting the run time for the collections sorting method
        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the unsorted list
        long elapsedTime = (endTime - startTime);
        //getting the value for the run time
        System.out.printf("My Collections-Sort Time: %.2f milliseconds" , elapsedTime / Math.pow(10,6));
        //printing the value for the run time and converting from nanoseconds to milliseconds
        System.out.println("\nThe sorted list using collections:  " + list);
        //printing the sorted list
        array = backUpArray.clone();
        //setting the array back to the un-sorted list



        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the un-sorted list
        System.out.printf("My Selection-Sort Time: %.2f milliseconds" ,  selectionSort(array) / Math.pow(10,6) );
        //getting the run-time of the selection-sort method
        System.out.println("\nThe sorted list using selection sort: " + Arrays.toString(array));
        //printing the sorted list
        array = backUpArray.clone();
        //setting the array back to the un-sorted list



        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the un-sorted list
        System.out.printf("My Bubble-Sort Time: %.2f milliseconds" , bubbleSort(array) / Math.pow(10,6));
        //getting the run time of the bubble sort method
        System.out.println("\nThe sorted list using bubble sort: " + Arrays.toString(array));
        //printing the sorted list
        array = backUpArray.clone();
        //setting the array back to the un-sorted list



        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the un-sorted list
        System.out.printf("My Insertion-Sort Time: %.2f milliseconds " ,  insertionSort(array) / Math.pow(10,6));
        //getting the run time of the selection-sort method
        System.out.println("\nThe sorted list using insertion sort: " + Arrays.toString(array));
        //printing the sorted list
        array = backUpArray.clone();
        //setting the array back to the un-sorted list




        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the un-sorted list
        System.out.printf("My Merge-Sort Time: %.2f milliseconds" , mergeSort(array) / Math.pow(10,6));
        //getting the run time of the merge-sort method
        System.out.println("\nThe sorted list using merge sort: " + Arrays.toString(array));
        //printing the sorted array
        array = backUpArray.clone();
        //setting the array back to the unsorted list


        System.out.println("\nThe unsorted list: " +Arrays.toString(backUpArray));
        //printing the unsorted list
        System.out.printf("My Quick-Sort Time: %.2f milliseconds" , quickSort(array,0, array.length - 1) / Math.pow(10,6));
        //getting the run time of the quick-sort method
        System.out.println("\nThe sorted list using quick sort: " + Arrays.toString(array));
        //printing the sorted list
        array = backUpArray.clone();
        //resetting the array to the un-sorted list

        //System.out.println("\nThe unsorted list: " + Arrays.toString(backUpArray));
        //prints unsorted list
        //Integer[] bucketSortArray = Arrays.copyOf(backUpArray, backUpArray.length);
        //creating integer type bucketSortArray
        //System.out.printf("My Bucket-Sort Time: %.2f milliseconds", bucketSort(bucketSortArray, 0, bucketSortArray.length - 1, 2) / Math.pow(10, 6));
        //prints the runtime
       // System.out.println("\nThe sorted list using bucket sort: " + Arrays.toString(bucketSortArray));
        //prints sorted list
        //backUpArray = Arrays.copyOf(bucketSortArray, bucketSortArray.length);
        // Updates the backup array

        myFooter(6);











    }
    public static <T extends Comparable<? super T>> long selectionSort(T[] a) {
        long startTime = System.nanoTime();
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            //finding the minimum index

            for (int j = i + 1; j < n; j++) {
                if (a[j].compareTo(a[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            //finding the minimum index


            // Swapping elements in the array
            T temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
            // Swapping elements in the array using a temporary node
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
        //returning the runtime
    }



    public static <T extends Comparable<? super T>> long bubbleSort(T[] a) {
        long startTime = System.nanoTime();
        int n = a.length;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                //traversing through all the elements in the array
                if (a[j].compareTo(a[j + 1]) > 0) {
                    T temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }  // Compare adjacent elements and swap them if they are in the wrong order
            }
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
        //returning the runtime

    }


    public static <T extends Comparable<? super T>> long insertionSort(T[] a) {
        long startTime = System.nanoTime();
        int n = a.length;

        for (int i = 1; i < n; i++) {
            T key = a[i];
            int j = i - 1;
            //traversing through all the elements in the array


            while (j >= 0 && a[j].compareTo(key) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
            // // Move elements of array[0..i-1] that are greater than key to one position ahead of their current position
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
        //returning the runtime
    }




    public static <T extends Comparable<? super T>> long mergeSort(T[] S) {
        long startTime = System.nanoTime();
        mergeSort(S, 0, S.length - 1);
        long endTime = System.nanoTime();
        return endTime - startTime;
        //using the merge-sort algorithm for a generic type T
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] S, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(S, low, mid);
            mergeSort(S, mid + 1, high);
            merge(S, low, mid, high);
        }
        //using a helper method for the merge-sort method
    }

    private static <T extends Comparable<? super T>> void merge(T[] S, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;


        T[] L = Arrays.copyOfRange(S, low, mid + 1);
        T[] R = Arrays.copyOfRange(S, mid + 1, high + 1);
        //creating temporary arrays

        int i = 0, j = 0, k = low;

        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                S[k++] = L[i++];
            } else {
                S[k++] = R[j++];
            }
        }//merging the two temporary arrays


        while (i < n1) {
            S[k++] = L[i++];
        }
        // Copying the remaining elements of L[] and R[]

        while (j < n2) {
            S[k++] = R[j++];
        }
        //incrementing the index positions
    }


    public static <T extends Comparable<? super T>> long quickSort(T[] s, int a, int b) {
        long startTime = System.nanoTime();
        if (a < b) {
            int partitionIndex = partition(s, a, b);
            quickSort(s, a, partitionIndex - 1);
            quickSort(s, partitionIndex + 1, b);
        }
        //using the quick-sort algorithm for generic type T
        long endTime = System.nanoTime();
        return endTime - startTime;
    }//returning the runtime

    private static <T extends Comparable<? super T>> int partition(T[] s, int a, int b) {
        T pivot = s[b];
        int i = a - 1;
        for (int j = a; j < b; j++) {
            if (s[j].compareTo(pivot) <= 0) {
                i++;
                swap(s, i, j);
            }
        }
        swap(s, i + 1, b);
        return i + 1;
    }//creating a helper method to for the quicksort

    private static <T> void swap(T[] s, int i, int j) {
        T temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
    //swapping the elements using a temp node
//    public static long bucketSort(Integer[] a, int first, int last, int maxDigits) {
//            long startTime = System.nanoTime();
//            //recording the start time
//            int maxValue = findMaxValue(a);
//            //finding max value in the array to determine the number of digits
//
//            for (int exp = 1; maxValue / exp > 0 && exp <= maxDigits; exp *= 10) {
//                ArrayList<Integer>[] buckets = new ArrayList[10];
//                //iterating through each digits place
//
//                for (int i = 0; i < 10; i++) {
//                    buckets[i] = new ArrayList<>();
//                }
//
//                for (int i = first; i <= last; i++) {
//                    int bucketIndex = (a[i] / exp) % 10;
//                    buckets[bucketIndex].add(a[i]);
//                }
//
//                int index = first;
//                for (ArrayList<Integer> bucket : buckets) {
//                    Collections.sort(bucket);
//                    for (int value : bucket) {
//                        a[index++] = value;
//                    } // Placing all the buckets back into the array
//                }
//            }
//            long endTime = System.nanoTime();
//            return endTime - startTime;
//        } // Returns the runtime

        private static int findMaxValue(Integer[] array) {
            int max = array[0];
            for (int value : array) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }//helper method to find ith max value




        public static void myHeader(String nm, int sn, String dn) {
        System.out.println("======================================================================");
        System.out.print("Full name: " + nm + "\nStudent number: " + sn + "\nGoal of Exercise: " + dn);
        System.out.println("\n======================================================================");
    }//header method

    public static void myFooter(int n) {
        System.out.println("\n======================================================================");
        System.out.println("Completion of Lab Exercise " + n + "-Q1 is successful");
        System.out.println("Signing off - Caelan");
        System.out.println("======================================================================");
    }//footer method



    }











