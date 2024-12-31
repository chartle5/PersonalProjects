package LE6Q2;

public  class StudentGrade implements  Comparable<StudentGrade>{

    private String firstName;
    private String lastName;
    private int grade;
    //creating fields for the firstName, lastName and grade

    public StudentGrade(){}
    //constructor without arguments

    public StudentGrade(String firstName, String lastName, int grade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }//constructor with arguments

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    //creating the getter and setter methods

    @Override
    public int compareTo(StudentGrade other){
        return  Integer.compare(this.grade, other.grade);
    }
    //creating the compareTo method

    @Override
    public String toString(){
        return "Name: " + firstName + " "+lastName+ ", Grade: "+ grade;
    }
    //toString method to format the output

}

