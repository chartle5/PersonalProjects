package se2203.chartle5_assignment1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HydraHeadsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HydraHeadsApplication.class.getResource("HydraHeads-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        stage.setTitle("Hydra Game");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/se2203/chartle5_assignment1/HydraIcon.png"));


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}