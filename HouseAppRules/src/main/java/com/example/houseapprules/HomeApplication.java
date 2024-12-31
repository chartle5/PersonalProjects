package com.example.houseapprules;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        stage.setTitle("House Discipline Game");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/houseapprules/WesternLogo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}