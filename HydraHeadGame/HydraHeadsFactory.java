package se2203.chartle5_assignment1;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class HydraHeadsFactory {

    private static Slider headSizeSlider;
    private static Image headSize1 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize1.png");
    private static Image headSize2 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize2.png");
    private static Image headSize3 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize3.png");
    private static Image headSize4 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize4.png");
    private static Image headSize5 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize5.png");
    private static Image headSize6 = new Image("file:src/main/resources/se2203/chartle5_assignment1/HeadSize6.png");

    private HydraHeadsFactory() {
    }

    public static HydraHead getHead(int size) {


       if(size == 2){
           return new HydraHead(headSize2,2);
       }
       else if(size == 3){
           return new HydraHead(headSize3, 3);
       }
       else if(size == 4){
           return new HydraHead(headSize4, 4);
       }
       else if(size == 5){
           return new HydraHead(headSize5,5);
       }
       else if(size == 6){
           return new HydraHead(headSize6, 6);
       }
       else{
           return new HydraHead(headSize1, 1);
       }

       //instantiating HydraHead images for each size




    }

}


