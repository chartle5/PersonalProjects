package LA7Q1;

import java.util.Scanner;

public class CaelanDemoHashingWithLinearAddressing {

    public static int items;
    public static Scanner input = new Scanner(System.in);
    public static double lf;
    public static int tableCapacity;
    //creating fields for load factor, table capacity and the data items
    public static CaelanValueEntry[] hashtable;
    public static CaelanValueEntry[] workingHashTable;

    // Creating array type fields for the hash tables

    public static void addValueLinearProbe(Integer key){

        int arrayIndex = key % tableCapacity;
        // creating a variable for the array index where "key" should be inputted

        if (arrayIndex < 0){
            arrayIndex += tableCapacity;
        } // making sure the value is positive after using the modulus

        while (workingHashTable[arrayIndex].getKey() != -111){
            arrayIndex++; // moving to the next index if the index is not free

            if (arrayIndex == workingHashTable.length){
                arrayIndex = 0;
            } // if the index gets to the end of the array, sets it back to the beginning

        }

        workingHashTable[arrayIndex].setKey(key);
        // sets the available index to the integer inputted

    }


    public static int checkPrime(int n) {

        int m = n / 2; // only need to check the numbers less than half the integer
        for (int i = 3; i <= m; i++) {
            if (n % i == 0) { // if n is not a prime number
                i = 2; // set i to 2 so that it is incremented to 3 in the for-header
                n++; // incrementing the n value
                m = n / 2; // only need to check half of the n factors
            }
        }
        return n;
    }


    public static void removeValueLinearProbe(Integer key){

        for (int i = 0; i < workingHashTable.length; i++) { // iterating over each element of the array
            if (workingHashTable[i].getKey() == key){ // if the element is equal to the one were looking for;
                workingHashTable[i].setKey(-111); // remove it from the list
                System.out.print(key + " is Found and removed! ");
                break; // once the number were looking for is removed, the loop breaks and the method ends
            }
        }

    }
    public static void printHashTable(){

        System.out.print("The Current Hash-Table: [");

        for (int i = 0; i < workingHashTable.length; i++) {
            // iterates through each element of the array

            if (workingHashTable[i].getKey() == -1){
                System.out.print("null, "); // if the value of the key is -1, print null

            } else if (workingHashTable[i].getKey() == -111) {
                System.out.print("available, "); // if the value is -111, print available

            } else {
                System.out.print(workingHashTable[i].getKey() + ", ");
            } // if the value is neither of the above, print the value of it

        }
        System.out.println("\b\b]");
    }

    public static void rehashingWithLinearProbe(){

        hashtable = new CaelanValueEntry[tableCapacity];
        // creates new array of new size if it's changed

        for (int i = 0; i < hashtable.length; i++) {
            hashtable[i] = new CaelanValueEntry();
        } // adds "null" value for each element

        for (int i = 0; i < workingHashTable.length; i++) {
            // iterates through array

            if (workingHashTable[i].getKey() != -1){
                // if the index is not null

                int arrayIndex = workingHashTable[i].getKey() % tableCapacity;
                // creating variable for the array index of where the key should be

                if (arrayIndex < 0){
                    arrayIndex += tableCapacity;
                } // makes sure the value of the mod is positive

                if (workingHashTable[i].getKey() == -111){
                    workingHashTable[i].setKey(-1);
                } // if the index is available, make it null

                while (hashtable[arrayIndex].getKey() != -1) {
                    arrayIndex++; // if the array index is not null, move to the next index

                    if (arrayIndex == hashtable.length) {
                        arrayIndex = 0; // If the index gets to the end of the array sets it back to the beginning
                    }

                }

                hashtable[arrayIndex] = workingHashTable[i];
                // Sets the value to its new hashed index

            }

        }

        workingHashTable = hashtable;
        // Sets the original array to the new one

    }

    public static void main(String[] args) {
        myHeader("Caelan Hartley", 251283976, "Today we are working with hash tables using linear adressing");

        System.out.println("Let's decide on the initial table capacity based on the load factor and dataset size");
        System.out.print("How many data items: ");
        items = input.nextInt();
        System.out.print("What is the load factor (Recommended: <= 0.5): ");
        lf = input.nextDouble();
        // prompting user to enter amount of items and load factor

        tableCapacity = checkPrime((int) (items/lf)); // uses user inputted values to determine table capacity
        System.out.println("The minimum required table capacity would be: " + tableCapacity);
        workingHashTable = new CaelanValueEntry[tableCapacity];
        // creating array of CaelanValueEntry type with size of table capacity


        for (int i = 0; i < items; i++) {
            System.out.print("Enter item " + (i + 1) + ": ");
            workingHashTable[i] = new CaelanValueEntry(input.nextInt());
        } // Enters user inputted amount of user inputted items

        for (int i = items; i < workingHashTable.length; i++) {
            workingHashTable[i] = new CaelanValueEntry();
        } // fills the rest of the hashtable with null values

        rehashingWithLinearProbe(); // rehashes the hash table
        printHashTable(); // prints hash table


        System.out.println("\nLet's remove two values from the table and then add one......\n");


        System.out.print("Enter a value you want to remove: ");
        removeValueLinearProbe(input.nextInt());
        printHashTable();
        // removes an item and makes that index available, then prints the updated array

        System.out.print("Enter a value you want to remove: ");
        removeValueLinearProbe(input.nextInt());
        printHashTable();
        // removes another item, makes that index available, then prints the updated array

        System.out.print("Enter a value to add to the table: ");
        addValueLinearProbe(input.nextInt());
        printHashTable();
        // adds an item in one of the empty indices, then prints array

        System.out.println("\nRehashing the table...");
        tableCapacity = checkPrime(tableCapacity*2);
        System.out.println("The rehashed table capacity is: " + tableCapacity);
        //finds the new table capacity and rehashes the table
        rehashingWithLinearProbe();
        printHashTable();
        myFooter(7);
        //printing the rehashed table and the footer


        }
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


