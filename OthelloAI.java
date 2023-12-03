import szte.mi.Move;
import szte.mi.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OthelloAI implements Player {
    public int order;
    public Random random;
    public OthelloGame game;


    public OthelloAI(OthelloGame game) {
        this.game = game;
    }

    @Override
    public void init(int order, long t, Random rnd) {
        this.order = order;
        this.random = rnd;
    }


    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        OthelloGame gameCopy = new OthelloGame(0);
        gameCopy.setBoard(Arrays.stream(game.getBoard()).map(int[]::clone).toArray(int[][]::new));
        gameCopy.setCurrentPlayer(order);

        int maxDepth = 4; // Set maximum search depth

        Move bestMove = getBestMove(gameCopy, maxDepth);
        boolean validMoveFound = false;

        while (!validMoveFound) {
            bestMove = getBestMove(gameCopy, maxDepth);
            int bestMoveX = bestMove.x;
            int bestMoveY = bestMove.y;

            // Check if the selected move is valid
            if (gameCopy.isValidMove(bestMoveX, bestMoveY)) {
                validMoveFound = true;
            } else {
                // Retry with a new move if the selected move is invalid
                System.out.println("AI made an invalid move. Retrying...");
            }
        }

        return bestMove;
    }



    public Move getBestMove(OthelloGame game, int maxDepth) {
        Object[] result = alphaBeta(game, maxDepth, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return (Move) result[1];
    }


    public Object[] alphaBeta(OthelloGame game, int maxDepth, boolean maximizingPlayer, double alpha, double beta) {
        if (maxDepth == 0 || game.isGameOver()) {
            return new Object[]{evaluateGameState(game), null};
        }

        List<Move> validMoves = game.getValidMoves();

        if (maximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            Move bestMove = null;

            for (Move move : validMoves) {
                OthelloGame newGame = new OthelloGame(game.getPlayerMode());
                newGame.setBoard(Arrays.stream(game.getBoard()).map(int[]::clone).toArray(int[][]::new));
                newGame.setCurrentPlayer(game.getCurrentPlayer());
                newGame.makeMove(move.x, move.y);

                Object[] evalResult = alphaBeta(newGame, maxDepth - 1, false, alpha, beta);
                double eval = (double) evalResult[0];

                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove = move;
                }

                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }

            return new Object[]{maxEval, bestMove};
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            Move bestMove = null;

            for (Move move : validMoves) {
                OthelloGame newGame = new OthelloGame(game.getPlayerMode());
                newGame.setBoard(Arrays.stream(game.getBoard()).map(int[]::clone).toArray(int[][]::new));
                newGame.setCurrentPlayer(game.getCurrentPlayer());
                newGame.makeMove(move.x, move.y);

                Object[] evalResult = alphaBeta(newGame, maxDepth - 1, true, alpha, beta);
                double eval = (double) evalResult[0];

                if (eval < minEval) {
                    minEval = eval;
                    bestMove = move;
                }

                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }

            return new Object[]{minEval, bestMove};
        }
    }

    public static double evaluateGameState(OthelloGame game) {

        double coinParityWeight = 1.0;
        double mobilityWeight = 2.0;
        double cornerOccupancyWeight = 5.0;
        double stabilityWeight = 3.0;
        double edgeOccupancyWeight = 2.5;


        int playerDiskCount = Arrays.stream(game.getBoard()).flatMapToInt(Arrays::stream).filter(disk -> disk == game.getCurrentPlayer()).sum();
        int opponentDiskCount = Arrays.stream(game.getBoard()).flatMapToInt(Arrays::stream).filter(disk -> disk == -game.getCurrentPlayer()).sum();
        double coinParity = playerDiskCount - opponentDiskCount;


        int playerValidMoves = game.getValidMoves().size();
        int opponentValidMoves = new OthelloGame(1).getValidMoves().size();
        double mobility = playerValidMoves - opponentValidMoves;


        int cornerOccupancy = Arrays.stream(new int[][]{{0, 0}, {0, 7}, {7, 0}, {7, 7}})
                .mapToInt(coords -> game.getBoard()[coords[0]][coords[1]])
                .sum();

        double stability = calculateStability(game);


        int edgeOccupancy = Arrays.stream(new int[]{0, 7})
                .flatMap(i -> Arrays.stream(game.getBoard()[i], 1, 7))
                .sum()
                + Arrays.stream(new int[]{0, 7})
                .flatMap(i -> Arrays.stream(game.getBoard(), 1, 7).mapToInt(row -> row[i]))
                .sum();


        return coinParity * coinParityWeight
                + mobility * mobilityWeight
                + cornerOccupancy * cornerOccupancyWeight
                + stability * stabilityWeight
                + edgeOccupancy * edgeOccupancyWeight;
    }

    public static int calculateStability(OthelloGame game) {

        int stableCount = 0;


        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] corner : new int[][]{{0, 0}, {0, 7}, {7, 0}, {7, 7}}) {
            int row = corner[0];
            int col = corner[1];
            if (game.getBoard()[row][col] == game.getCurrentPlayer() && isStableDisk(game, row, col, directions)) {
                stableCount++;
            }
        }

        for (int row = 2; row <= 5; row++) {
            for (int col = 2; col <= 5; col++) {
                if (game.getBoard()[row][col] == game.getCurrentPlayer() && isStableDisk(game, row, col, directions)) {
                    stableCount++;
                }
            }
        }

        return stableCount;
    }

    private static boolean isStableDisk(OthelloGame game, int row, int col, int[][] directions) {
        for (int[] direction : directions) {
            int dr = direction[0];
            int dc = direction[1];
            int newRow = row + dr;
            int newCol = col + dc;

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                if (game.getBoard()[newRow][newCol] != game.getCurrentPlayer()) {
                    return false;
                }
                newRow += dr;
                newCol += dc;
            }
        }
        return true;
    }
}