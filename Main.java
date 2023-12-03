import szte.mi.Move;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize the game and AI
        OthelloGame game = new OthelloGame(0);
        OthelloAI ai = new OthelloAI(game);
        game.setCurrentPlayer(1); // Set the player to black
        ai.init(-1, System.currentTimeMillis(), new Random()); // Set the AI to white


        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Main game loop
        while (!game.isGameOver()) {
            // Print the current board and valid moves
            printGameState(game);

            // Player's turn
            System.out.println("Your turn (Black). Enter your move (row col): ");
            int playerRow = scanner.nextInt();
            int playerCol = scanner.nextInt();
            Move playerMove = new Move(playerRow, playerCol);

            // Make the player's move
            game.makeMove(playerMove.x, playerMove.y);

            // Print the updated board
            printGameState(game);

            // Check for game over after player's move
            if (game.isGameOver()) {
                break;
            }

            // AI's turn
            System.out.println("AI's turn (White):");
            Move aiMove = ai.nextMove(playerMove, System.currentTimeMillis(), System.currentTimeMillis());

            // Make the AI's move
            game.makeMove(aiMove.x, aiMove.y);

            // Print the updated board
            printGameState(game);
        }

        // Print the final result
        printGameState(game);
        printGameResult(game);
    }

    // Helper method to print the current game state
    private static void printGameState(OthelloGame game) {
        System.out.println("Current Board:");
        printBoard(game.getBoard());
        System.out.print("Valid Moves: ");
        for (Move move : game.getValidMoves()) {
            System.out.print("(" + move.x + ", " + move.y + ") ");
        }
        System.out.println();
    }

    // Helper method to print the game result
    private static void printGameResult(OthelloGame game) {
        int winner = game.getWinner();
        if (winner == 1) {
            System.out.println("You win!");
        } else if (winner == -1) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    // Helper method to print the game board
    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "B " : (cell == -1 ? "W " : ". "));
            }
            System.out.println();
        }
        System.out.println();
    }
}