package buttongame;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;


public class Spacegame_button extends Button{




    private final String FONT_PATH = "https://drive.google.com/uc?export=download&id=12G0M6Mz4FRUcTHQCiyRMe_3phrJlPVT1";



    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('https://drive.google.com/uc?export=download&id=1Ceu4mOnPP54CY4pNKZthBSFV0Vp4Laxr')";


    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('https://drive.google.com/uc?export=download&id=1ThsH-jQiwtPQBaGbsSAhGweVPowv870b')";







    public Spacegame_button(String text){
        setText(text);
        setButtonFont();
        setPrefHeight(49);
        setPrefWidth(190);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }
    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        } catch (FileNotFoundException e) {
            //throw new RuntimeException(e);

            setFont(Font.font("Verdana",23));
        }
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }
    private void setButtonReleasedStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() -4);
    }


    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }



}
