package space;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InfoLabel extends Label{
    public final static String FONT_PATH = "C:/Users/umama/Desktop/temp python/demo5/src/main/java/fonts/RobotoCondensed-Italic.ttf";


    public InfoLabel(String text) {
        setPrefWidth(600);
        setPrefHeight(49);
        setPadding(new Insets(10, 40, 40, 50));
        setText(text);
        setWrapText(true);
        setLabelFont();
        setTextFill(Color.YELLOW);
        setAlignment(Pos.CENTER);




    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
        } catch (FileNotFoundException e) {
            System.out.println("Could not load font. Using defaults...");
            setFont(Font.font("Verdana", 25));
        }
    }
}
