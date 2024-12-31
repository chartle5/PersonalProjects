package Sorting;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class Caelan_SortNameAndGrade {

    public static void main(String[] args) {

        myHeader("Caelan Hartley", 251284172, "Creating a application that sorts by first name, last name and grade");
        String[] fnArray = {"Hermione", "Ron", "Harry", "Luna", "Ginny", "Draco", "Dean", "Fred"};
        String[] lnArray = {"Granger", "Weasley", "Potter", "Lovegood", "Weasley", "Malfoy", "Thomas", "Weasley"};

        Integer[] grd = {(int)(60 + Math.random()*26),(int)(60 + Math.random()*26),
                (int)(60 + Math.random()*26),(int)(60 + Math.random()*26),
                (int)(60 + Math.random()*26),(int)(60 + Math.random()*26),
                (int)(60 + Math.random()*26),(int)(60 + Math.random()*26)};

        Vector<StudentGrade> sg = new Vector<>();
        //creating vector to store StudentGrade objects

        for (int i = 0; i < fnArray.length; i++) {
            sg.add(new StudentGrade(fnArray[i], lnArray[i], grd[i]));
        }
        //storing the StudentGrade objects into an array

        System.out.println("Unsorted Lists:");
        for (StudentGrade student : sg){
            System.out.println(student);
        }//printing the un-sorted lists

        Collections.sort(sg);
        //sorting the list of StudentGrade objects

        System.out.println("\n"+"Sorted by Grade:");
        for (StudentGrade student : sg){
            System.out.println(student);
        }
        //printing the sorted list by grades

        StudentGrade[] sgArray = new StudentGrade[fnArray.length];

        sg.copyInto(sgArray);
        //creating an array and copying vector elements into the array

        InsertionSort(sgArray, 1);

        printArray(sgArray,"Sorted list by First Name:");
        //printing the array using insertion sort sorting my first names

        InsertionSort(sgArray, 2);

        printArray(sgArray, "Sorted list by Last Name");
        //printing the array using insertion sort sorting by last name

        myFooter(6);
        //printing the footer



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


    public static void InsertionSort(StudentGrade[] array, int key) {
        int n = array.length;

        for (int j = 1; j < n; j++) {
            StudentGrade keyStudent = array[j];
            int i = j-1;
            //iterating over the array

            while (i> -1 && compareStudents(array[i], keyStudent, key)>0){
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = keyStudent;
        }//comparing and swapping elements by their given key (firstName, lastName)
    }

    private static int compareStudents(StudentGrade student1, StudentGrade student2, int key){
        if (key == 1){
            return student1.getFirstName().compareTo(student2.getFirstName());
        } else if (key ==2) {
            return student1.getLastName().compareTo(student2.getLastName());
        }else {
            throw new IllegalArgumentException("Invalid value");
        }
        //using the compareTo helper method to compare firstNames and lastNames to be sorted
    }

    public static void printArray(StudentGrade[] array, String message){
        System.out.println("\n" + message);
        for (StudentGrade student : array){
            System.out.println(student);
        }
    }//printing and formatting the array of StudentGrade objects

}
