package com.example.houseapprules;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private Button cancelBtn;

    @FXML
    private Label errorMsg;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField username;

    private DataStore accountTable;
    private DataStore profileTable;
    private HomeController homeController;

    public void setDataStore(DataStore accountAdapter, DataStore profileAdapter) {
        accountTable = accountAdapter;
        profileTable = profileAdapter;
    }


    public void authorize() {
        errorMsg.setText("");
        try {
            Account account = (Account) accountTable.findRecord(username.getText());
            if (account.getUsername() == null) {
                errorMsg.setText("Username doesn't exist");

            } else {
                String password = account.getPassword();
                if (password.equals(account.getPassword())) {
                    authenticate(account, account.getAccountType());

                } else {
                    errorMsg.setText("Incorrect password");
                }
            }
        } catch (SQLException e) {
        }


    }

    public void authenticate(Account account, String accountType) {
        try {
            Profile profile = (Profile) profileTable.findRecord(account);
            homeController.setUsername(account.getUsername());
            if (accountType.equals("admin")) {
                homeController.adminControls();
                homeController.setName(profile.getFirstName());
            } else {

                homeController.userControls();
                homeController.setName(profile.getFirstName());
            }
        } catch (SQLException e) {
        }
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();

    }



    public void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setHomeController(HomeController controller){
        homeController = controller;
    }


}
