
    package Q1;
    public class Passenger {
        String fName;
        String lName;
        int row;
        char seat;
        String priority;
        //creating fields for the passengers properties

        public Passenger(){}
        //constructor with no argument

        public Passenger(String fName, String lName,int row, char seat){
            this.fName = fName;
            this.lName = lName;
            this.row = row;
            this.seat = seat;

        }//constructor with arguments for the passenger

        public int getRow(){
            return row;
        }//getRow method so the rows can be compared

        public char getSeat(){
            return seat;
        }
        @Override
        public String toString() {
            return String.format(fName + " " + lName + " (row: " + row + " seat: " + seat + ")" );
        }//overriden to String method to print Passenger objects in the arrayList


    }

