package com.example.houseapprules;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileController {
    @FXML
    private DatePicker DOB;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField studentNumber;

    private HomeController homeController;

    private ProfileTableAdapter profileTableAdapter;

    private DataStore accountTable;
    private DataStore profileTable;

    public ProfileController(){
        try{
            profileTableAdapter = new ProfileTableAdapter(false);
        }catch (SQLException e){}
    }

    public void setDataStore(DataStore accountAdapter, DataStore profileAdapter){
        accountTable = accountAdapter;
        profileTable = profileAdapter;
    }



    public void setHomeController(HomeController controller){
        homeController = controller;
    }

    @FXML
    public void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void save() {
        try {
            Profile profile = new Profile();
            profile.setStudentNumber(this.studentNumber.getText());
            profile.setFirstName(this.firstName.getText());
            profile.setLastName(this.lastName.getText());
            profile.setPhoneNumber(this.phoneNumber.getText());
            profile.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            profileTableAdapter.addNewRecord(profile);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }
}
