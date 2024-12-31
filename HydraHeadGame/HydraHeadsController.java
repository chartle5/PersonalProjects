package se2203.chartle5_assignment1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class HydraHeadsController {
    @FXML
    private Button resetButton;

    @FXML
    private Button playButton;
    @FXML
    private Slider headSizeSlider;

    @FXML
    private Label totalCutsLabel;
    @FXML
    private GridPane gridPane;
    private int numberOfCuts = 0;


    @FXML
    public void initialize() {
        headSizeSlider.setValue(4);


    }

    @FXML
    public void reset(ActionEvent event) {
        headSizeSlider.setValue(4);
        playButton.setDisable(false);
        headSizeSlider.setDisable(false);
        numberOfCuts = 0;
        totalCutsLabel.setText("");
        gridPane.getChildren().clear();


    }

    public void play(ActionEvent event) {
        playButton.setDisable(true);
        headSizeSlider.setDisable(true);

        generateHead(HydraHeadsFactory.getHead(setHeadSize()));


    }

    private void generateHead(HydraHead head) {
        int randX, randY;
        boolean CellIsEmpty = false;
        while (!CellIsEmpty) {
            randX = new Random().nextInt(16);
            randY = new Random().nextInt(16);

            if (!isGridUsed(randX, randY)) {
                head.putIn(randX, randY, gridPane);
                CellIsEmpty = true;
            }
        }
        head.setOnMouseClicked(this::getNextHead);
    }//generating the HydraHead


    public int setHeadSize() {
        return (int) headSizeSlider.getValue();
    }

    private void getNextHead(MouseEvent event) {
        HydraHead thisHead = (HydraHead) event.getSource();
        if (thisHead.getHeadSize() > 1) {
            Random random = new Random();
            int numberOfHeads = random.nextInt(2) + 2;
            //creating either 2 or 3 smaller HydraHeads


            for (int i = 0; i < numberOfHeads; i++) {
                int gridX, gridY;

                do {
                    gridX = random.nextInt(16);
                    gridY = random.nextInt(16);
                } while (isGridUsed(gridX, gridY));
                //generates a location until an available cell is found

                HydraHead nextHead = HydraHeadsFactory.getHead(thisHead.getHeadSize() - 1);
                nextHead.setOnMouseClicked(this::getNextHead);
                nextHead.putIn(gridX, gridY, gridPane);
                //putting in a smaller HydraHead once the location is found


            }

        }

        gridPane.getChildren().remove(thisHead);
        numberOfCuts++;


        if (gridPane.getChildren().isEmpty()) {
            totalCutsLabel.setDisable(false);
            totalCutsLabel.setText("Good Job! - you have cut " + numberOfCuts + " Hydra Heads");
        }//displaying number of cuts label


    }


    private boolean isGridUsed(int targetGridX, int targetGridY) {

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) != null &&
                    GridPane.getRowIndex(node) != null &&
                    GridPane.getColumnIndex(node) == targetGridX &&
                    GridPane.getRowIndex(node) == targetGridY) {
                return true;
            }
        }
        return false;
        //returns false if there's already a HydraHead

    }
}



