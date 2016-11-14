package com.chaudhry.najeeb.tictactoe;

import java.util.Random;

public class TicTacToeGame {

    private char mBoard[];
    private final static int BOARD_SIZE = 9;
    public static final char HUMAN_PLAYER = 'X';
    public static final char ANDROID_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';
    private java.util.Random mRand;


    public static int getBoardSize() {
        return BOARD_SIZE;
    }


    ///Constructor
    public TicTacToeGame() {
        mBoard = new char[BOARD_SIZE];  //initialize mBoard[9] that is 0 to 8
        //Assign each item in array to empty char that is ' '
        for (int i=0; i<BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;

        mRand = new Random();
    }


    //Just like in constructor, assign each item in array to empty char that is ' '
    public void clearBoard() {
        for (int i=0; i<BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;
    }


    public void setMove(char player, int location) {
        mBoard[location] = player;
    }


    public int getComputerMove() {
        int move;

        for (int i=0; i<getBoardSize(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = ANDROID_PLAYER;
                //Check if by placing X in mBoard[i] android wins, if not then replace X by empty space in mBoard[i]
                int[] winner = checkForWinner();
                if (winner[0] == 3) {
                    setMove(ANDROID_PLAYER,i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        for (int i=0; i<getBoardSize(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                //Check if by placing O in mBoard[i] human wins, if not then replace O by empty space in mBoard[i]
                int[] winner = checkForWinner();
                if (winner[0] == 2) {
                    setMove(ANDROID_PLAYER, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        do {
            move = mRand.nextInt(getBoardSize());   //get random number between 0 & 9
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == ANDROID_PLAYER);

        setMove(ANDROID_PLAYER, move);

        return move;    //return index, move is index of char array
    }


    public int[] checkForWinner() {
        //check for human winner or android winner in buttons 0 1 2 then 3 4 5 then 6 7 8
        for (int i=0; i<=6; i+=3) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i+1] == HUMAN_PLAYER && mBoard[i+2] == HUMAN_PLAYER)
                //Returning array of int.
                //First value in array, 2, represents the winner.  That is human is winner
                //Rest values in array, i i+1 i+2, are the locations of cells/buttons that make a winner line
                return new int[] {2, i, i+1, i+2};   //Human is winner
            if (mBoard[i] == ANDROID_PLAYER && mBoard[i+1] == ANDROID_PLAYER && mBoard[i+2] == ANDROID_PLAYER)
                //Returning array of int.
                //First value in array, 3, represents the winner.  That is android is winner
                //Rest values in array, i i+1 i+2, are the locations of cells/buttons that make a winner line
                return new int[] {3, i, i+1, i+2};   //Android is winner
        }

        //check for human winner or android winner in buttons 0 3 6 then 1 4 5 then 2 5 8
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i+3] == HUMAN_PLAYER && mBoard[i+6] == HUMAN_PLAYER)
                //Returning array of int.
                //First value in array, 2, represents the winner.  That is human is winner
                //Rest values in array, i i+3 i+6, are the locations of cells/buttons that make a winner line
                return new int[] {2, i, i+3, i+6};   //Human is winner
            if (mBoard[i] == ANDROID_PLAYER && mBoard[i+3] == ANDROID_PLAYER && mBoard[i+6] == ANDROID_PLAYER)
                //Returning array of int.
                //First value in array, 3, represents the winner.  That is android is winner
                //Rest values in array, i i+3 i+6, are the locations of cells/buttons that make a winner line
                return new int[] {3, i, i+3, i+6};   //Android is winner
        }

        //check for human winner in buttons 0 4 8
        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER)
            //Returning array of int.
            //First value in array, 2, represents the winner.  That is human is winner
            //Rest values in array, 0 4 8, are the locations of cells/buttons that make a winner line
            return new int[] {2, 0, 4, 8};   //Human is winner

        //check for human winner in buttons 2 4 6
        if (mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER)
            //Returning array of int.
            //First value in array, 2, represents the winner.  That is human is winner
            //Rest values in array, 2 4 6, are the locations of cells/buttons that make a winner line
            return new int[] {2, 2, 4, 6};   //Human is winner

        //check for android winner in buttons buttons 0 4 8
        if (mBoard[0] == ANDROID_PLAYER && mBoard[4] == ANDROID_PLAYER && mBoard[8] == ANDROID_PLAYER)
            //Returning array of int.
            //First value in array, 3, represents the winner.  That is android is winner
            //Rest values in array, 0 4 8, are the locations of cells/buttons that make a winner line
            return new int[] {3, 0, 4, 8};   //Android is winner

        //check for android winner in buttons 2 4 6
        if (mBoard[2] == ANDROID_PLAYER && mBoard[4] == ANDROID_PLAYER && mBoard[6] == ANDROID_PLAYER)
            //Returning array of int.
            //First value in array, 3, represents the winner.  That is android is winner
            //Rest values in array, 2 4 6, are the locations of cells/buttons that make a winner line
            return new int[] {3, 2, 4, 6};   //Android is winner

        //If code reached to this point then it means so far there are no winners
        //Check if empty buttons left, if yes then return 0 meaning there is no winner and there is still an empty spot/button
        for (int i=0; i<getBoardSize(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
                return new int[] {0};   //No one is  winner and there is still empty spot/button
        }

        //If human is not winner and android is not winner and no empty spot left then it is a tie
        return new int[] {1};
    }
}
