import szte.mi.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OthelloGame {
    public int[][] board;
    public int currentPlayer;
    public int playerMode;

    public OthelloGame(int playerMode) {
        this.board = new int[8][8];
        this.board[3][3] = 1;
        this.board[3][4] = -1;
        this.board[4][3] = -1;
        this.board[4][4] = 1;
        this.currentPlayer = 1;
        this.playerMode = playerMode;
    }
    public void setBoard(int[][] newBoard) {
        this.board = Arrays.stream(newBoard).map(int[]::clone).toArray(int[][]::new);
    }

    public int[][] getBoard() {
        return Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public int getPlayerMode() {
        return playerMode;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }



    public boolean isValidMove(int row, int col) {
        if (board[row][col] != 0) {
            return false;
        }

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            int r = row + dr;
            int c = col + dc;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == -currentPlayer) {
                r += dr;
                c += dc;

                if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == currentPlayer) {
                    return true;
                }
            }
        }

        return false;
    }

    public void flipDisks(int row, int col) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            int r = row + dr;
            int c = col + dc;
            List<int[]> flipList = new ArrayList<>();

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == -currentPlayer) {
                flipList.add(new int[]{r, c});
                r += dr;
                c += dc;

                if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == currentPlayer) {
                    for (int[] flipCoord : flipList) {
                        int fr = flipCoord[0];
                        int fc = flipCoord[1];
                        board[fr][fc] = currentPlayer;
                    }
                }
            }
        }
    }

    public void makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            board[row][col] = currentPlayer;
            flipDisks(row, col);
            currentPlayer *= -1;
        }
    }

    public boolean isGameOver() {
        return getValidMoves().isEmpty() || isBoardFull();
    }

    public int getWinner() {
        int blackCount = countDisks(1);
        int whiteCount = countDisks(-1);

        if (blackCount > whiteCount) {
            return 1;
        } else if (blackCount < whiteCount) {
            return -1;
        } else {
            return 0;
        }
    }

    public List<Move> getValidMoves() {
        List<Move> validMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(row, col)) {
                    validMoves.add(new Move(row, col));
                }
            }
        }
        return validMoves;
    }


    public boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int countDisks(int player) {
        int count = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == player) {
                    count++;
                }
            }
        }
        return count;
    }
    public int countBlackDisks() {
        return countDisks(1);
    }
    public int countWhiteDisks() {
        return countDisks(-1);
    }

}

