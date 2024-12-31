package com.example.houseapprules;

import javafx.beans.property.*;

import java.util.Date;

public class Profile {

    private final StringProperty studentNumber;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    private final ObjectProperty<Date> DOB;
    private final DoubleProperty deductions;

    private ObjectProperty<Account> account = new SimpleObjectProperty<>(new Account());


    public Profile(){
        this.studentNumber = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.DOB = new SimpleObjectProperty<>();
        this.deductions = new SimpleDoubleProperty();

    }

    public void setAccount(Account account){
        this.account.set(account);
    }

    public Account getAccount(){
        return this.account.get();
    }

    public String getStudentNumber() {
        return studentNumber.get();
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber.set(studentNumber);
    }

    public StringProperty studentNumberProperty() {
        return studentNumber;
    }

    // Getters and Setters for firstName
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    // Getters and Setters for lastName
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    // Getters and Setters for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    // Getters and Setters for DOB
    public Date getDOB() {
        return DOB.get();
    }

    public void setDOB(Date DOB) {
        this.DOB.set(DOB);
    }

    public ObjectProperty<Date> DOBProperty() {
        return DOB;
    }

    // Getters and Setters for deductions
    public double getDeductions() {
        return deductions.get();
    }

    public void setDeductions(double deductions) {
        this.deductions.set(deductions);
    }

    public DoubleProperty deductionsProperty() {
        return deductions;
    }



}

