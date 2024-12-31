package com.example.houseapprules;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HomeController {
    @FXML
    private MenuItem createProfileMenu;

    @FXML
    private MenuItem deleteProfileMenu;

    @FXML
    private MenuItem editLogsMenu;

    @FXML
    private MenuItem exitBtn;

    @FXML
    private MenuItem fileReportMenu;
    @FXML
    private MenuItem createAccountMenu;
    @FXML
    private MenuItem deleteAccountMenu;

    @FXML
    private Menu homeMenu;

    @FXML
    private MenuItem loginMenu;

    @FXML
    private MenuItem logoutMenu;

    @FXML
    private MenuItem modifyProfileMenu;

    @FXML
    private Menu profileMenu;

    @FXML
    private MenuItem reportLogMenu;

    @FXML
    private Menu reportsMenu;
    @FXML
    private Menu userMenu;

    private Connection connection;

    private DataStore account;

    private String username;
    private String name;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String firstName){
        this.name = firstName;
    }

    public String getName(){
        return name;
    }

    public void login() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("Login.fxml"));
        Parent login = fxmlLoader.load();
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.setHomeController(this);
        loginController.setDataStore(new AccountTableAdapter(false), new ProfileTableAdapter(false));

        Stage stage = new Stage();
        stage.setScene(new Scene(login));
        stage.getIcons().add(new Image("file:src/main/resources/com/example/houseapprules/WesternLogo.png"));
        stage.setTitle("Login to your Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void createProfile() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("AddProfile.fxml"));
        Parent addProfile = fxmlLoader.load();
        ProfileController profileController = (ProfileController) fxmlLoader.getController();
        profileController.setHomeController(this);
        profileController.setDataStore(new AccountTableAdapter(false), new ProfileTableAdapter(false));

        Stage stage = new Stage();
        stage.setScene(new Scene(addProfile));
        stage.getIcons().add(new Image("file:src/main/resources/com/example/houseapprules/WesternLogo.png"));
        stage.setTitle("Create your Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void createAccount() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("AddAccount.fxml"));
        Parent addUser = fxmlLoader.load();
        AccountController accountController = (AccountController) fxmlLoader.getController();
        accountController.setHomeController(this);
        accountController.setDataStore(new AccountTableAdapter(false), new ProfileTableAdapter(false));

        Stage stage = new Stage();
        stage.setScene(new Scene(addUser));
        stage.getIcons().add(new Image("file:src/main/resources/com/example/houseapprules/WesternLogo.png"));
        stage.setTitle("Create your Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void adminControls(){
        createProfileMenu.setDisable(false);
        deleteProfileMenu.setDisable(false);
        createAccountMenu.setDisable(false);
        deleteAccountMenu.setDisable(false);
        editLogsMenu.setDisable(false);
        exitBtn.setDisable(false);
        fileReportMenu.setDisable(false);
        homeMenu.setDisable(false);
        loginMenu.setDisable(false);
        logoutMenu.setDisable(false);
        modifyProfileMenu.setDisable(false);
        profileMenu.setDisable(false);
        reportLogMenu.setDisable(false);
        reportsMenu.setDisable(false);
    }

    public void userControls(){
        createProfileMenu.setDisable(false);
        deleteProfileMenu.setDisable(true);
        createAccountMenu.setDisable(false);
        deleteAccountMenu.setDisable(true);
        editLogsMenu.setDisable(true);
        exitBtn.setDisable(false);
        fileReportMenu.setDisable(false);
        homeMenu.setDisable(false);
        loginMenu.setDisable(false);
        logoutMenu.setDisable(false);
        modifyProfileMenu.setDisable(false);
        profileMenu.setDisable(false);
        reportLogMenu.setDisable(false);
        reportsMenu.setDisable(false);
    }

    public void disableMenu(){
        reportsMenu.setDisable(true);
        profileMenu.setDisable(false);
        createProfileMenu.setDisable(false);
        homeMenu.setDisable(false);
        logoutMenu.setDisable(true);
        loginMenu.setDisable(false);
    }

    public void initialize(){
        disableMenu();
        try{
            String db_url = "jdbc:derby:HouseRules;create=true";
            connection = DriverManager.getConnection(db_url);
        }catch (SQLException e){

        }
    }
}

