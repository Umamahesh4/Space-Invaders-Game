package mainmenu_sub;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.scene.text.Text;

import java.awt.*;

import static java.awt.Color.*;

public class Mainmenusub_selection extends SubScene {

    private final static String FONT_PATH = "https://drive.google.com/uc?export=download&id=159uzFNV3zQ1RXWzksrQv0xxfyfU3yB9d";

    private final static String BACKGROUND_IMAGE = "https://drive.google.com/file/d/1QzQ0DGIP006qCbFYsFJqhrOHZPHsKO8d/view?usp=sharing";


    private boolean isHidden;

    private Text credittext;


    public Mainmenusub_selection() {
        super(new AnchorPane(),600,400);
        prefHeight(400);
        prefWidth(600);
        BackgroundImage image=new BackgroundImage(new Image(BACKGROUND_IMAGE,600,400,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        AnchorPane subMainRoot = (AnchorPane) this.getRoot();

        subMainRoot.setBackground(new Background(image));

        isHidden=true;

        setLayoutX(1024);
        setLayoutY(180);






    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.3));

        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-676);
            isHidden=false;
        }
        else {
            transition.setToX(0);
            isHidden=true;
        }


        transition.play();
    }

    public AnchorPane getPane(){
        return(AnchorPane) this.getRoot();
    }
}
