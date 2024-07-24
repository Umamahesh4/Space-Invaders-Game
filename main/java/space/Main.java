package space;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import viewgame.ViewManager;
import javafx.scene.image.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ask for the password
            if (askForPassword()) {
                // Password is correct, proceed to the game
                ViewManager manager = new ViewManager();
                primaryStage.setScene(manager.getMainScene());
                primaryStage.show();




            } else {
                // Incorrect password, close the application
                primaryStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean askForPassword() {

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");



        StackPane passwordPane = new StackPane();
        passwordPane.getChildren().add(passwordField);

        Alert passwordDialog = new Alert(Alert.AlertType.CONFIRMATION);
        passwordDialog.setTitle("Enter Password");
        passwordDialog.setHeaderText(null);
        passwordDialog.getDialogPane().setContent(passwordPane);

        // Show the password dialog and wait for the user's response
        passwordDialog.showAndWait();

        // Check if the entered password is correct ("welcome")
        return passwordDialog.getResult() == ButtonType.OK && "welcome".equals(passwordField.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
