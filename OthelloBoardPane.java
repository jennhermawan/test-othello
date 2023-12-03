/**

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoardPane extends GridPane {

    private OthelloGame game;
    private OthelloAI ai;

    private List<OnCellClickListener> cellClickListeners = new ArrayList<>();

    public OthelloBoardPane(OthelloGame game, OthelloAI ai) {
        this.game = game;
        this.ai = ai;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = createCellButton(row, col);
                add(button, col, row);
            }
        }
        setAlignment(Pos.CENTER);
    }

    private Button createCellButton(int row, int col) {
        Button button = new Button();
        button.setMinSize(40, 40); // Set the size of each cell
        button.setOnAction(event -> handleCellClick(row, col));
        updateCellButton(button, row, col);
        return button;
    }

    private void updateCellButton(Button button, int row, int col) {
        int disc = game.getBoard()[row][col];
        if (disc == 1) {
            button.setStyle("-fx-background-color: #000000;"); // Black for black discs
        } else if (disc == -1) {
            button.setStyle("-fx-background-color: #FFFFFF;"); // White for white discs
        } else {
            button.setStyle("-fx-background-color: #8DB6CD;"); // Light blue for empty cells
        }
    }

    private void handleCellClick(int row, int col) {
        for (OnCellClickListener listener : cellClickListeners) {
            listener.onCellClick(row, col);
        }
    }

    public void setOnCellClickListener(OnCellClickListener listener) {
        cellClickListeners.add(listener);
    }

    public interface OnCellClickListener {
        void onCellClick(int row, int col);
    }
}
*/