package com.example.houseapprules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AccountController {
    @FXML
    private Button cancelBtn;

    @FXML
    private TextField confirmPassword;

    @FXML
    private TextField password;

    @FXML
    private Button saveBtn;

    @FXML
    private ChoiceBox<String> studentNumbers;

    @FXML
    private TextField username;

    private AccountTableAdapter accountTableAdapter;

    private ProfileTableAdapter profileTableAdapter;

    private HomeController homeController;

    private DataStore accountTable;
    private DataStore profileTable;


    public void setDataStore(DataStore accountAdapter, DataStore profileAdapter){
        accountTable = accountAdapter;
        profileTable = profileAdapter;
        populateList();
    }

    public AccountController(){
        try{
            accountTableAdapter = new AccountTableAdapter(false);
            profileTableAdapter = new ProfileTableAdapter(false);
        }catch (SQLException e){}
    }

    public void setHomeController(HomeController controller){
        homeController = controller;
    }

    public void populateList() {
        try {
            List<String> list = profileTable.getKeys();
            studentNumbers.setItems(FXCollections.observableArrayList(list));

            if (!list.isEmpty()) {
                studentNumbers.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
        }
    }


    @FXML
    public void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void save(){
        try {
            Account account = new Account();
            account.setUsername(username.getText());
            account.setPassword(password.getText());
            if (username.getText().equals("chartle5")){
                account.setAccountType("admin");
            } else {
                account.setAccountType("user");
            }
            accountTableAdapter.addNewRecord(account);

            String studentNumber = studentNumbers.getValue();
            Profile profile = (Profile) profileTableAdapter.findRecord(studentNumber);
            profile.setAccount(account);
            profileTableAdapter.updateRecord(profile);

        } catch (SQLException e) {

        }
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }
    public void initialize(URL url, ResourceBundle resource) {
        populateList();
    }


}

