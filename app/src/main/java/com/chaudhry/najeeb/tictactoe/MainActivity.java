package com.chaudhry.najeeb.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //define instance variables for the widgets
    private com.chaudhry.najeeb.tictactoe.TicTacToeGame ticTacToeGame;
    private Button mBoardButtons[];
    private TextView whoseTurnTextView;
    private TextView youCountTextView;
    private TextView tiesCountTextView;
    private TextView androidCountTextView;
    private int mHumanCounter = 0;
    private int mTieCounter = 0;
    private int mAndroidCounter = 0;
    private boolean mHumanFirst = true;
    private boolean mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create button array
        mBoardButtons = new Button[ticTacToeGame.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.oneButton);
        mBoardButtons[1] = (Button) findViewById(R.id.twoButton);
        mBoardButtons[2] = (Button) findViewById(R.id.threeButton);
        mBoardButtons[3] = (Button) findViewById(R.id.fourButton);
        mBoardButtons[4] = (Button) findViewById(R.id.fiveButton);
        mBoardButtons[5] = (Button) findViewById(R.id.sixButton);
        mBoardButtons[6] = (Button) findViewById(R.id.sevenButton);
        mBoardButtons[7] = (Button) findViewById(R.id.eightButton);
        mBoardButtons[8] = (Button) findViewById(R.id.nineButton);

        //get references to widgets
        whoseTurnTextView = (TextView) findViewById(R.id.whoseTurnTextView);
        youCountTextView = (TextView) findViewById(R.id.youCountTextView);
        tiesCountTextView = (TextView) findViewById(R.id.tiesCountTextView);
        androidCountTextView = (TextView) findViewById(R.id.androidCountTextView);

        //write values in widgets
        youCountTextView.setText(Integer.toString(mHumanCounter));
        tiesCountTextView.setText(Integer.toString(mTieCounter));
        androidCountTextView.setText(Integer.toString(mAndroidCounter));

        //call constructor to create reference to TicTacToeGame.java class
        ticTacToeGame = new com.chaudhry.najeeb.tictactoe.TicTacToeGame();

        startNewGame();
    }


    //start a new game, clear board
    private void startNewGame() {

        ticTacToeGame.clearBoard();

        for (int i=0; i<mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        if (mHumanFirst)
        {
            whoseTurnTextView.setText(R.string.first_human);
            mHumanFirst = false;
        }
        else
        {
            whoseTurnTextView.setText(R.string.turn_computer);
            int move = ticTacToeGame.getComputerMove();
            setMove(ticTacToeGame.ANDROID_PLAYER, move);
            mHumanFirst = true;
        }
        mGameOver = false;

        //Original color of buttons is Gray 600 #757575.  If someone won then those buttons change color to
        //to Indigo A700 #304FFE.  In that case set color of all buttons back to Gray 600 #757575.
        mBoardButtons[0].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[1].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[2].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[3].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[4].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[5].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[6].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[7].setBackgroundColor(Color.parseColor("#757575"));
        mBoardButtons[8].setBackgroundColor(Color.parseColor("#757575"));
    }


    private class ButtonClickListener implements View.OnClickListener {

        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver)
            {
                if (mBoardButtons[location].isEnabled())
                {
                    setMove(ticTacToeGame.HUMAN_PLAYER, location);

                    int[] winner = ticTacToeGame.checkForWinner();

                    //No one is  winner and there is still empty spot/button
                    if (winner[0] == 0)
                    {
                        whoseTurnTextView.setText(R.string.turn_computer);
                        int move = ticTacToeGame.getComputerMove();
                        setMove(ticTacToeGame.ANDROID_PLAYER, move);
                        winner = ticTacToeGame.checkForWinner();
                    }

                    //winner[0]=0 means no one is  winner and there is still empty spot/button
                    if (winner[0] == 0)
                        whoseTurnTextView.setText(R.string.turn_human);
                    //winner[0]=1 means no human winner, no android winner and no empty spot left then it is a tie
                    else if (winner[0] == 1)
                    {
                        whoseTurnTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        tiesCountTextView.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                    }
                    //winner[0]=2 means human is winner
                    else if (winner[0] == 2)
                    {
                        whoseTurnTextView.setText(R.string.result_human_wins);
                        mHumanCounter++;
                        youCountTextView.setText(Integer.toString(mHumanCounter));
                        mGameOver = true;

                        //Change the background color of the winning buttons
                        mBoardButtons[winner[1]].setBackgroundColor(Color.parseColor("#304FFE"));
                        mBoardButtons[winner[2]].setBackgroundColor(Color.parseColor("#304FFE"));
                        mBoardButtons[winner[3]].setBackgroundColor(Color.parseColor("#304FFE"));
                    }
                    //winner[0]=3 means android is winner
                    else
                    {
                        whoseTurnTextView.setText(R.string.result_android_wins);
                        mAndroidCounter++;
                        androidCountTextView.setText(Integer.toString(mAndroidCounter));
                        mGameOver = true;

                        //Change the background color of the winning buttons
                        mBoardButtons[winner[1]].setBackgroundColor(Color.parseColor("#304FFE"));
                        mBoardButtons[winner[2]].setBackgroundColor(Color.parseColor("#304FFE"));
                        mBoardButtons[winner[3]].setBackgroundColor(Color.parseColor("#304FFE"));
                    }
                }
            }
        }
    }


    private void setMove(char player, int location)
    {
        ticTacToeGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == ticTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.newGame:
                startNewGame();
                break;
            case R.id.exitGame:
                MainActivity.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
