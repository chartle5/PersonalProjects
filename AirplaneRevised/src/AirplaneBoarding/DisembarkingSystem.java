package Q1;

import java.util.*;
import java.util.Collections;
import java.util.Comparator;

public class DisembarkingSystem {


    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {


        //I decided to create a boarding and disembarking app using one ArrayList for the passengers and a LinkedList for the boarding process
        //I organized the boarding and disembarking process by the passengers row, so that the front of the plane get priority when it comes to both boarding and disembarking
        //The linked list was excellent for the boarding/disembarking process as it follows a first in first out process which was ideal for what I was looking to do
        //There are also methods I used such as .poll() which are very easy to use and take information from the linkedList
        // The Array List is a very simple data structure to use and was perfect to store Passenger Type objects
        //I was also able to use the java collections and comparator to sort my ArrayList
        //In my Application once a passenger has boarded the plane they are removed from the boarding waiting list and transferred to the linked list with the other boarded members
        //The application also ensures that the seat is empty before booking


        ArrayList<Passenger> passengers = new ArrayList<>();
        Queue<Passenger> boarding = new LinkedList<>();
        //creating an ArrayList to store all the passengers information
        //creating a LinkedList to perform the boarding and disembarking functions

        myHeader("Caelan Hartley", 251283976, "Today we will be using an airplane boarding and disembarking application");
        //calling myHeader


        boolean q = true;

        while (q) {


            while (q) {
                int choice;
                //try {
                    System.out.println("Here is the menu, pick a choice!");


                    System.out.println("1. Add passenger to boarding waiting list");
                    System.out.println("2. Board Passenger");
                    System.out.println("3. Disembark Passenger");
                    System.out.println("4. Display Passenger list");
                    System.out.println("5. Exit ");
                    //creating a list of options for the user to choose from
                    if(input.hasNext()) {
                        choice = input.nextInt();
                    }
                    else{
                        System.out.println("Please enter valid choice");
                        input.nextLine();



                    //setting the users choice to be the next input

                    if(choice == 1|| choice == 2|| choice == 3|| choice == 4|| choice == 5){
                        break;

                    }
                    //else{
                        //throw new InputMismatchException();
                    }
                //}catch(InputMismatchException e){
                    System.out.println("Please enter valid choice");
                   input.nextLine();
                }



                switch (choice) {

                    case 1:
                        System.out.print("Enter first name: ");
                        String fName = input.next();
                        System.out.print("Enter last name: ");
                        String lName = input.next();
                        int row;
                        //collecting the passengers name

                        do {
                            System.out.print("Enter row(1-25): ");
                            row = input.nextInt();
                            if (row < 1 || row > 25) {
                                System.out.println("Invalid choice");

                            }
                        } while (row < 1 || row > 25);
                        //this do-while loop collects the row from the user validating that if a integer is given that it is between 1 and 25 I tried to use a try catch block to get rid of and error when a non integer type character si inputted but it kept creating errors for me


                        char seat;
                        boolean seatTaken = false;

                        do {
                            System.out.println("Pick your seat (A-F): ");
                            seat = input.next().toUpperCase().charAt(0);
                            if (seat < 'A' || seat > 'F') {
                                System.out.println("Invalid seat choice");
                            }
                        } while (seat < 'A' || seat > 'F');
                        //this do-while loop has the same function as the previous one getting information from the user, converting the input to uppercase and validating it fits within the range

                        for (Passenger x : passengers) {
                            if (x.getRow() == row && x.getSeat() == seat) {
                                seatTaken = true;
                                System.out.println("Seat " + seat + " in row " + row + " is already taken");
                                break;
                            }//validates that the seat is empty
                        }


                        Passenger p1 = new Passenger(fName, lName, row, seat);
                        passengers.add(p1);
                        System.out.println("Passenger " + fName + " has been added");
                        //creating a new Passenger object that has its name row and seat as parameters


                        Comparator<Passenger> rowComparator = Comparator.comparingInt(Passenger::getRow);
                        Collections.sort(passengers, rowComparator);
                        break;
                    //comparing passengers based on their row number so that the people at the front of the plane will board first


                    case 2:
                        System.out.println("Boarding starting from the front of the plane...");

                        if (!passengers.isEmpty()) {
                            Passenger boardedPassenger = passengers.get(0);
                            System.out.println(boardedPassenger + " has boarded the plane");
                            boarding.add(boardedPassenger);
                            passengers.remove(0);
                            //if there is elements in the array list the first element(closest to front of plane) will be added to a linked list that stores the passengers on the plane
                        } else {
                            System.out.println("There are no passengers to board");
                        }//if there are no passengers added it will display this message
                        break;


                    case 3:
                        System.out.println("Disembarking from the front of the plane...");
                        if (!boarding.isEmpty()) {
                            Passenger disembarkedPassenger = boarding.poll();
                            System.out.println(disembarkedPassenger + " has gotten off the plane");
                        } //if there are passengers on the plane the boarding.poll() method will grab the head of the linked list(closest to front of plane) and disembark them
                        else {
                            System.out.println("No passengers to disembark");
                        }//displays this message if no passengers have been boarded
                        break;

                    case 4:
                        System.out.println("Displaying passengers");
                        System.out.println(passengers);
                        //displaying the passengers arrayList
                        break;

                    case 5:
                        System.out.println("Thank you for using my application");
                        myFooter(5);
                        q = false;
                        break;
                    //ending the loop and the user input and displaying my footer
                }
            }

        }
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




