package JavaTechniques2;
import JavaTechniques.ValueEntry;
import static LA7Q1.CaelanDemoHashingWithLinearAddressing.*;

public class CaelanDemoHashingWithAllOpenAdressingTechniques {
    public static void addValueQuadraticProbe(Integer key) {

        int arrayIndex = key % tableCapacity;
        // creating variable for array index of key argument

        int count = 0;
        // creating variable for count

        if (arrayIndex < 0) {
            arrayIndex += tableCapacity;
        } // makes sure the value of the mod is positive

        while (workingHashTable[arrayIndex].getKey() != -1) {
            // loops if arrayIndex is occupied

            count++;
            // adds 1 to count every loop

            arrayIndex = key % tableCapacity;
            // resets arrayIndex variable

            arrayIndex += count * count;
            // adds count^2 to arrayIndex

            while (arrayIndex >= workingHashTable.length) {
                // ff array index is greater than size of array

                arrayIndex -= workingHashTable.length;
                // subtract the size of the array from the array index

            }
        }

        workingHashTable[arrayIndex].setKey(key);
        // sets arrayIndex value to key argument

    }


    public static void addValueDoubleHashing(Integer key) {

        int q = thePrimeNumberForSecondHashFunction(tableCapacity);
        // creating variable for the first prime number that comes before table capacity

        int arrayIndex = key % tableCapacity;
        // creates variable for array index of key argument

        if (arrayIndex < 0) {
            arrayIndex += tableCapacity;
        } // makes the value of the mod positive if it is negative

        int count = 0;
        // creating counter variable

        while (workingHashTable[arrayIndex].getKey() != -1) {
            // loops if arrayIndex is occupied

            count++;
            // adding 1 to count every loop

            int temp = (q - (key % q));
            // creating temp variable for second hash function

            arrayIndex = key % tableCapacity;
            // resetting arrayIndex variable

            arrayIndex += count * temp;
            // adding count multiplied by temp to array Index

            arrayIndex %= tableCapacity;
            // checks if array index within array size

            while (arrayIndex >= workingHashTable.length) {
                // ff array index is greater than size of array

                arrayIndex -= workingHashTable.length;
                // subtracting the size of the array from the array index

            }

        }

        workingHashTable[arrayIndex].setKey(key);
        // sets arrayIndex value to key argument

    }

    public static int thePrimeNumberForSecondHashFunction(int q) {

        int m = q / 2; //we just need to check half of the q factors
        q--; // subtracting 1 from q
        for (int i = m; i >= 3; i--) {
            if (q % i == 0) { // if q is not a prime number
                i = m + 1;
                q--; // previous q value
                m = q / 2;
            }
        }
        return q;
    }

    public static void emptyHashTable() {

        for (int i = 0; i < workingHashTable.length; i++) {
            workingHashTable[i].setKey(-1);
        } // sets all values in the array to null
    }

    public static void printArray(Integer[] array) {

        System.out.print("The given dataset is: [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        } // printing the given array
        System.out.println("\b\b]");
    }

    public static void main(String[] args) {

        myHeader("Caelan Hartley", 251283976, "Using all open adressing techniques with hash tables");

        System.out.println("Let's demonstrate our understanding on all the open addressing techniques...");
        System.out.print("How many data items: ");
        items = input.nextInt();
        System.out.print("What is the load factor (Recommended: <= 0.5): ");
        lf = input.nextDouble();
        // prompting user to enter amount of items and load factor

        tableCapacity = checkPrime((int) (items / lf));
        System.out.println("The minimum required table capacity would be: " + tableCapacity);
        // using user inputs to determine table capacity

        workingHashTable = new CaelanValueEntry[tableCapacity];
        // creating array of table capacity size
        Integer[] array = {-11, 22, -33, 44, -55};


        for (int i = 0; i < array.length; i++) {
            workingHashTable[i] = new CaelanValueEntry(array[i]);
        } // filling Hashtable with Array elements

        for (int i = array.length; i < workingHashTable.length; i++) {
            workingHashTable[i] = new CaelanValueEntry();
        } // filling remainder of hashtable with null elements

        System.out.print("The given dataset is: ");
        printArray(array); // printing array
        System.out.println("Let's enter each data item from the above to the hash table: ");


        System.out.println("\nAdding data - linear probing resolves collision");
        rehashingWithLinearProbe(); // hashes array


        printHashTable(); // prints hashed array
        emptyHashTable(); // empties hash table


        System.out.println("\nAdding data - quadratic probing resolves collision");
        if (lf > 0.5) { // if load factor is higher than 0.5, terminate program
            System.out.println("Probing failed! Use a load factor of 0.5 or less. Goodbye!");
            myFooter(7); // prints footer
            System.exit(0); // terminates program
        }
        for (int i = 0; i < array.length; i++) {
            addValueQuadraticProbe(array[i]);
        } // adding each value of the array to Hash table using quadratic probe method

        printHashTable(); // prints hash table
        emptyHashTable(); // empties hash table


        System.out.println("\nAdding data - double-hashing resolves collision");
        System.out.println("The q value for double hashing is: " + thePrimeNumberForSecondHashFunction(tableCapacity));
        for (int i = 0; i < array.length; i++) {
            addValueDoubleHashing(array[i]);
        } // adding each value of the array using double hashing method


        printHashTable(); // prints the hash table
        myFooter(7);

    }
    public static void myHeader(String nm, int sn, String dn) {
        System.out.println("======================================================================");
        System.out.print("Full name: " + nm + "\nStudent number: " + sn + "\nGoal of Exercise: " + dn);
        System.out.println("\n======================================================================");
    }//header method

    public static void myFooter(int n) {
        System.out.println("\n======================================================================");
        System.out.println("Completion of Lab Exercise " + n + "-Q2 is successful");
        System.out.println("Signing off - Caelan");
        System.out.println("======================================================================");
    }//footer method
}
