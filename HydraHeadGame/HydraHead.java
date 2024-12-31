package se2203.chartle5_assignment1;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class HydraHead extends ImageView {

    private Image image;

    private ImageView imageview;
    private int headSize;
    private int currentGridX;
    private int currentGridY;


    public HydraHead(Image image, int size){
        this.image = image;
        this.headSize = size;
        this.setFitHeight(40);
        this.setFitWidth(40);


    }

    public int getHeadSize(){
        return headSize;
    }



    public int getGridX(){
        return currentGridX;
    }

    public int getGridY(){
        return currentGridY;
    }

    public void putIn(int gridX, int gridY, GridPane board){

        board.add(this, gridX, gridY);
        this.setImage(image);

        //putting in the HydraHead image onto the gridPane




    }




}

