
package viewgame;






import buttongame.Spacegame_button;
import gameplay.game_play.StarBlazersGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mainmenu_sub.Mainmenusub_selection;
import space.InfoLabel;

import java.util.ArrayList;
import java.util.List;



public class ViewManager {

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private static final int HEIGHT =650;
    private static final int WIDTH =1024;

    private final static int MENU_BUTTONS_START_X=100;
    private final static int MENU_BUTTONS_START_Y=200;
    private final static int MENU_BUTTONS_SPACING=20;

    private Mainmenusub_selection creditSubScene;
    private Mainmenusub_selection helpSubScene;
    private Mainmenusub_selection scoreSubscene;
    private Mainmenusub_selection tohidescene;


    List<Spacegame_button> menuButtons;

    private StarBlazersGame starblazergame;

//    private JFXPanel

//    private Label hellolabel;


    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane=new AnchorPane();
        mainScene=new Scene(mainPane,WIDTH,HEIGHT);
        mainStage=new Stage();


        mainStage.setScene(mainScene);
        createbutton();
        createbackground();
        mainStage.show();

    }


    public Scene getMainScene() {
        return mainScene;
    }

    private void createbutton(){

        createStartButton();
        createCreditsButton();
        createScoreButton();
        createHelpButton();
        createExitButton();

        createSubScene();


    }

    private void showSubScene(Mainmenusub_selection subScene){
        if(tohidescene!=null){
            tohidescene.moveSubScene();
        }
        subScene.moveSubScene();
        tohidescene=subScene;
    }


    private void createbackground(){
        Image backgroundImage = new Image("https://drive.google.com/uc?export=download&id=18bxGQv4CjyUqjBAuNSqbCuWCTQEwZuPS", WIDTH, HEIGHT, false, true);



        BackgroundSize backgroundSize = new BackgroundSize(WIDTH,HEIGHT,false,false,false,true);


        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,null);

        mainPane.setBackground(new Background(background));
    }

    private void addMenuButtons(Spacegame_button button){
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y+ menuButtons.size()*(button.getPrefHeight()+MENU_BUTTONS_SPACING));
        menuButtons.add(button);
        mainPane.getChildren().add(button);

    }

    private void createStartButton(){
        Spacegame_button startbutton = new Spacegame_button("Start");
        addMenuButtons(startbutton);

        startbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                starblazergame=new StarBlazersGame();

                starblazergame.setVisible(true);
            }
        });





    }
    private void createScoreButton(){
        Spacegame_button scorebutton = new Spacegame_button("Scores");
        addMenuButtons(scorebutton);

        scorebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                scoreSubscene.moveSubScene();
                showSubScene(scoreSubscene);
            }
        });

    }
    private void createHelpButton(){
        Spacegame_button helpbutton = new Spacegame_button("Help");
        addMenuButtons(helpbutton);

        helpbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                helpSubScene.moveSubScene();
                showSubScene(helpSubScene);
                String temp1 ="Start Game: Press S to start.\n";
                String temp2="Movement: Use the left and right arrow keys \n";
                String temp3="Shoot: Press the space bar to shoot bullets\n";
                String temp4="Pause/Resume Menu: Press ESC to pause,E to resume";
                String temp5="Restart Game: Press R to restart the game \n";
                String temp6="Quit Game: Press Q to exit the game ";

                showText11(temp1);
                showText12(temp2);
                showText13(temp3);
                showText14(temp4);
                showText15(temp5);
                showText16(temp6);

            }
        });

    }
    private void createCreditsButton(){
        Spacegame_button creditsbutton = new Spacegame_button("Credits");
        addMenuButtons(creditsbutton);

        creditsbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp1 ="G CHAITRA SAI - CB.EN.U4CSE22521";
                String temp2="KV DEEPAK - CB.EN.U4CSE22521";
                String temp3="K N MOHAN GUPTA - CB.EN.U3CSE22524";
                String temp4="P UMA MAHESH - CB.EN.U4CSE22534";
                showText2(temp2);
                showText(temp1);
                showText3(temp3);
                showText4(temp4);

                showSubScene(creditSubScene);

            }
        });
    }
    private void showText(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(85);
        hellolabel.setLayoutX(30);
        creditSubScene.getPane().getChildren().add(hellolabel);
    }


    private void showText2(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(125);
        hellolabel.setLayoutX(35);
        creditSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText3(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(165);
        hellolabel.setLayoutX(30);
        creditSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText4(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(205);
        hellolabel.setLayoutX(30);
        creditSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText11(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(45);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText12(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(85);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText13(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(135);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText14(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(175);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText15(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(215);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void showText16(String text){
        InfoLabel hellolabel = new InfoLabel(text);
        hellolabel.setLayoutY(255);
        hellolabel.setLayoutX(30);
        helpSubScene.getPane().getChildren().add(hellolabel);
    }
    private void createExitButton(){
        Spacegame_button exitbutton = new Spacegame_button("Exit");
        addMenuButtons(exitbutton);
        exitbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Exit button clicked");
                Platform.exit();


            }
        });

    }

    private void createSubScene(){

        creditSubScene = new Mainmenusub_selection();
        mainPane.getChildren().add(creditSubScene);


        helpSubScene = new Mainmenusub_selection();
        mainPane.getChildren().add(helpSubScene);

        scoreSubscene = new Mainmenusub_selection();
        mainPane.getChildren().add(scoreSubscene);














    }



}



