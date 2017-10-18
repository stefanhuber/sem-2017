package fhkue.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView playerView;
    protected TextView turnView;
    protected Button[] board = new Button[9];
    protected int turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup layoutBoard = (ViewGroup) findViewById(R.id.board);
        for (int i = 0; i < layoutBoard.getChildCount(); i++) {
            board[i] = (Button) layoutBoard.getChildAt(i);
            board[i].setOnClickListener(this);
        }

        playerView = (TextView) findViewById(R.id.player);
        turnView = (TextView) findViewById(R.id.turn);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if (b.getText().equals("")) {
            b.setText(getCurrentPlayer());
        }
        checkWinner();
    }

    public void reset(View v) {
        this.resetGame();
    }

    protected void checkWinner() {
        String winner = "";

        // check cols
        for (int c = 0; c < 3; c++) {
            if (board[c].getText().equals(board[c+3].getText()) && board[c].getText().equals(board[c+6].getText())) {
                winner = board[c].getText().toString();
                break;
            }
        }

        // check rows
        for (int r = 0; r <= 6; r += 3) {
            if (board[r].getText().equals(board[r+1].getText()) && board[r].getText().equals(board[r+2].getText())) {
                winner = board[r].getText().toString();
                break;
            }
        }

        // check diag
        if (board[0].getText().equals(board[4].getText()) && board[0].getText().equals(board[8].getText())) {
            winner = board[0].getText().toString();
        }
        if (board[2].getText().equals(board[4].getText()) && board[2].getText().equals(board[6].getText())) {
            winner = board[2].getText().toString();
        }

        if (winner.equals("")) {
            ++turn;
            updateGame();
        } else {
            Toast.makeText(this, "Winner is player: " + getCurrentPlayer(), Toast.LENGTH_SHORT).show();
            resetGame();
        }
    }

    protected String getCurrentPlayer() {
        return (turn % 2 == 0) ? "O" : "X";
    }

    protected void updateGame() {
        playerView.setText("Player: " + getCurrentPlayer());
        turnView.setText("Turn: " + turn);
    }

    protected void resetGame() {
        this.turn = 1;
        for (int i = 0; i < board.length; i++) {
            board[i].setText("");
        }
        updateGame();
    }

}
