
// this code worked
/**
import java.util.List;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

public class MainOthello {
    public static void main(String[] args) {
        OthelloGame game = new OthelloGame("friend");
        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            printBoard(game);

            System.out.println("Current player: " + (game.currentPlayer == 1 ? "Black" : "White"));

            List<szte.mi.Move> validMoves = game.getValidMoves();

            System.out.print("Enter your move (row col): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (isValidMove(validMoves, row, col)) {
                game.makeMove(row, col);
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        printBoard(game);

        int winner = game.getWinner();
        System.out.println("Black Disks: " + game.countBlackDisks());
        System.out.println("White Disks: " + game.countWhiteDisks());

        if (winner == 1) {
            System.out.println("Black wins!");
        } else if (winner == -1) {
            System.out.println("White wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }

    private static void printBoard(OthelloGame game) {
        System.out.println("Current Board:");
        for (int[] row : game.board) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "B " : (cell == -1 ? "W " : ". "));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isValidMove(List<szte.mi.Move> validMoves, int row, int col) {
        for (szte.mi.Move move : validMoves) {
            if (move.x == row && move.y == col) {
                return true;
            }
        }
        return false;
    }
}
*/

