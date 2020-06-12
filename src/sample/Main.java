package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class Main extends Application {

    Rectangle[][] squares = new Rectangle[3][3];
    int [][] state = new int[3][3];

    Button start;
    VBox v;

    RadioButton easy, med, dif;

    @Override
    public void start(Stage primaryStage){
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);

        //Drawing the grid
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Rectangle square = new Rectangle(120, 120);
                square.setFill(Color.LIGHTSEAGREEN);
                square.setStrokeType(StrokeType.INSIDE);
                square.setStroke(Paint.valueOf("Black"));
                final int m = i;
                final int n = j;


                //Clickable rectangles
                square.setOnMouseClicked(event -> {
                    state[m][n] = 1;
                    solve(2);
                });

                squares[i][j] = square;
                board.add(square, j, i);
            }
        }

        //Creating the difficulty toggle group
        Label label = new Label("Select Difficulty");

        ToggleGroup difficulty = new ToggleGroup();
        easy = new RadioButton("Easy");
        med = new RadioButton("Medium");
        dif = new RadioButton("Difficult");
        easy.setToggleGroup(difficulty);
        med.setToggleGroup(difficulty);
        dif.setToggleGroup(difficulty);

        HBox selection = new HBox();
        selection.getChildren().addAll(easy, med, dif);
        selection.setSpacing(20);
        selection.setAlignment(Pos.CENTER);

        //Restart button
        start = new Button("Restart");
        start.setOnAction(this::handle);
        v = new VBox(15);
        v.setAlignment(Pos.CENTER);
        v.getChildren().addAll(label, selection, board, start);

        //Setting the stage
        primaryStage.setScene(new Scene(v, 450, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Method for drawing x and o according to current state
    private void draw() {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 1) {
                    Image x = new Image("file:C:\\Users\\norchi\\Documents\\Artificial intelligence\\src\\sample\\x.jpg");
                    this.squares[i][j].setFill(new ImagePattern(x));

                } else if (state[i][j] == 2) {
                    Image o = new Image("file:C:\\Users\\norchi\\Documents\\Artificial intelligence\\src\\sample\\o.jpg");
                    this.squares[i][j].setFill(new ImagePattern(o));
                }
                else
                    this.squares[i][j].setFill(Color.LIGHTSEAGREEN);
            }
        }
    }


    //Method called on every click of the player
    private void solve(int player){
        AI algorithm = new AI();
        if(algorithm.hasEnded(state) == 1){
            v.getChildren().set(0, new Label("Player 1 won!"));
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++)
                    squares[i][j].setOnMouseClicked(event -> {});
            }
            draw();
            return;
        }

        //Choosing difficulty
        int depth = 9;
        if(easy.isSelected()) depth = 2;
        if(med.isSelected()) depth = 5;
        if(dif.isSelected()) depth = 9;
        State newState = algorithm.limitedMinimax(state, player, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
                state[i][j] = newState.move[i][j];
        }

        draw();

        //Check if it has ended for player 1
        if(algorithm.hasEnded(newState.move) == 1){
            v.getChildren().set(0, new Label("Player 1 won!"));
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++)
                    squares[i][j].setOnMouseClicked(event -> {});
            }
        }

        //Check if the game has ended for player 2
        if(algorithm.hasEnded(newState.move) == 2){
            v.getChildren().set(0, new Label("Player 2 won!"));
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++)
                    squares[i][j].setOnMouseClicked(event -> {});
            }
        }
    }

    //Handle the button click - Restart the game
    private void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == start){
            state = new int[3][3];
            draw();
            v.getChildren().set(0, new Label("Select difficulty: "));
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++) {
                    final int m = i;
                    final int n = j;

                    squares[i][j].setOnMouseClicked(event -> {
                        state[m][n] = 1;
                        solve(2);
                    });
                }
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
