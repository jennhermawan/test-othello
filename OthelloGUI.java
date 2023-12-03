/**

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;

public class OthelloGUI extends Application {

    private OthelloGame game;
    private OthelloAI ai;
    private OthelloBoardPane boardPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Othello Game");

        // Create OthelloGame instance
        game = new OthelloGame("friend");

        // Create OthelloAI instance
        ai = new OthelloAI(game);

        // Create OthelloBoardPane with instances of OthelloGame and OthelloAI
        boardPane = new OthelloBoardPane(game, ai);

        // Create the root layout
        StackPane root = new StackPane();
        root.getChildren().add(boardPane);

        // Create the scene
        Scene scene = new Scene(root, 400, 400);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();

        // Start the game loop
        playGameLoop();
    }

    private void playGameLoop() {
        boardPane.setOnCellClickListener((row, col) -> {
            // Player's turn
            Move playerMove = new Move(row, col);
            game.makeMove(playerMove.x, playerMove.y);

            // Check for game over after player's move
            if (game.isGameOver()) {
                printGameResult();
                return;
            }

            // AI's turn
            Move aiMove = ai.nextMove(playerMove, System.currentTimeMillis(), System.currentTimeMillis());
            game.makeMove(aiMove.x, aiMove.y);

            // Check for game over after AI's move
            if (game.isGameOver()) {
                printGameResult();
            }
        });
    }

    private void printGameResult() {
        int winner = game.getWinner();
        if (winner == 1) {
            System.out.println("You win!");
        } else if (winner == -1) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
 */