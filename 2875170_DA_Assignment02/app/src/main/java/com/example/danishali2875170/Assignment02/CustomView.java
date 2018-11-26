package com.example.danishali2875170.Assignment02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.Random;
import android.os.Handler;
import com.example.danishali2875170.example28.R;

/**
 * This is a model View class to hold Custom View
 *
 * @see android.view.View
 * @author Danish Ali
 * @studentNumber 2875170
 * @since 2018-11-02
 * @version 1.0
 */

public class CustomView extends View {
    private PlayerControl cp;
    private Player p;
    private Paint blackPaint;
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private int colorcheck [];
    private int store [][];

    private CustomViewActivity cva;
    //@making click work
    private boolean[][] cellChecked;

    //@random numbers on board,state of cards
    private int [][] init;
    private STATES [][] state;
    private enum STATES {FLIPPED,FACEDOWN,FACEUP};




    //@random colour gen
    int[] rainbow = getContext().getResources().getIntArray(R.array.rainbow);
    Random ran = new Random();



    private Paint [][] colors;
    /**
     * Constructor initializes instance variables
     * @param context
     */
    public CustomView(Context context) {
        this(context, null);
        PlayerManagement();
    }
    /**
     * Constructor initializes instance variables
     * @param context
     * @param attrs
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PlayerManagement();

        blackPaint = new Paint();
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);



    }

    /**
     * Creating Game Board, Selecting Number of Columns to define game board
     * @param numColumns
     */
    public void SetgameColumns(int numColumns) {
        this.numColumns = numColumns;
        Re_adjustDimensions();
    }

    /**
     * Creating Game Board, Selecting Number of Rows to define game board
     * @param numRows
     */
    public void SetgameRows(int numRows) {
        this.numRows = numRows;
        Re_adjustDimensions();
    }


    @Override
    /**
     * In case screen size changes, readjust board demensions (According to Old HXW to New HXW)
     * @param w,h,oldw,oldh
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Re_adjustDimensions();
    }

    /**
     * Validating Columns/Rows, Figure Out (hxw) of each card(cell) <h>Logic Base Initialisations: </h>
     * <p>CellChecked boolean [][],
     * PlayerManagement int[][],
     * state STATES [][],
     * colors Paint[][],
     * store int [][]
     * invalidate()- Call</p>
     */
    private void Re_adjustDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth()/ numColumns;
        cellHeight = getWidth()/ numRows;

        cellChecked = new boolean[numColumns][numRows];
        init = new int [numColumns][numRows];
        state = new STATES[numColumns][numRows];
        colors = new Paint[numColumns][numRows];
        colorcheck = new int[2];
        store = new int[2][2];

        Random rand = new Random();
        for (int i = 0; i < numColumns; i++) {

            for (int j = 0; j < numRows; j++) {

                init[i][j] = rand.nextInt(+8);
                state[i][j] = STATES.FACEDOWN;
                colors[i][j]= new Paint();
                colors[i][j].setStyle(Paint.Style.FILL_AND_STROKE);
                colors[i][j].setColor(setrand());
            }
        }


        invalidate();
    }

    /**This method draw rectangle using drawRect, if cell/card is touched by player and state of card is !FLIPPED && drawLine is to make card borders
     * @see android.graphics.Canvas;
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * @code
         *
         */
        canvas.drawColor(Color.WHITE);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getWidth();





        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {



                if (cellChecked[i][j]) {
                    canvas.drawRect(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight, colors[i][j]);

                }

                if(state[i][j]==STATES.FLIPPED){
                    canvas.drawRect(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight, blackPaint);

                }


            }


        }


        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
        }

        for (int i = 1; i < numRows+1; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
        }


    }

    @Override
    /**
     *This method checks if click is within range, number of clicks are valide && board have some any cards.FACEDOWN. When clicked save Colour state of card, it's
     * location and its status. Upon second click, reduce clicks store colour state of card, its location and its status for match. Upon {@value cp.getPlayer().getClicks == 0}
     * compare both cards based on colour and their status. if matched disable clicks on them and count score and sums up total and update views and secondary methods
     * Once all cards are FLIPPED, a dialogue box will appear declaring winners && losers and game end options.
     * @param event
     * @return boolean
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);



                if (column < 4 && row < 4 && !(cp.getPlayer().getClicks()<=0)) {

                    if (!gameEnd(state)) {


                    if (state[column][row] == STATES.FACEDOWN) {
                        if (cp.getPlayer().getClicks() != 0) {

                            cellChecked[column][row] = !cellChecked[column][row];

                            state[column][row] = STATES.FACEUP;
                            if (cp.getPlayer().getClicks() == 2) {
                                colorcheck[0] = colors[column][row].getColor();
                                store[0][0] = column;
                                store[0][1] = row;
                                Toast.makeText(getContext(), "Same Colour Card : "+chances(colors[column][row])+"/16", Toast.LENGTH_LONG).show();
                            }
                            if (cp.getPlayer().getClicks() == 1) {
                                colorcheck[1] = colors[column][row].getColor();
                                store[1][0] = column;
                                store[1][1] = row;
                            }
                            cp.getPlayer().reduceClicks();
                            CustomViewActivity.UDATECLICKS(cp.getPlayer().getClicks());
                            invalidate();
                        }

                        if (cp.getPlayer().getClicks() == 0) {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (checkMatch(colorcheck[0], colorcheck[1])) {
                                        state[store[0][0]][store[0][1]] = STATES.FLIPPED;
                                        state[store[1][0]][store[1][1]] = STATES.FLIPPED;

                                        cp.UpdateScore(cp.getPlayer(), 1);
                                        CustomViewActivity.UPDATESCOREBOARD(cp.getPlayer().getName(), cp.getPlayer().getScore());
                                        CustomViewActivity.UPDATESMILEY(checkMatch(colorcheck[0], colorcheck[1]));
                                        AlertDialog(p,cp);

                                    } else {
                                       // Toast.makeText(getContext(), "Not Matched", Toast.LENGTH_SHORT).show();
                                        CustomViewActivity.UPDATESMILEY(checkMatch(colorcheck[0], colorcheck[1]));
                                        state[store[0][0]][store[0][1]] = STATES.FACEDOWN;
                                        state[store[1][0]][store[1][1]] = STATES.FACEDOWN;
                                        cellChecked[store[0][0]][store[0][1]] = !cellChecked[store[0][0]][store[0][1]];
                                        cellChecked[store[1][0]][store[1][1]] = !cellChecked[store[1][0]][store[1][1]];
                                        CustomViewActivity.UPDATESCOREBOARD(cp.getPlayer().getName(), cp.getPlayer().getScore());

                                    }
                                    invalidate();
                                    colorcheck = new int[2];
                                    store = new int[2][2];
                                    cp.SwitchPlayer();
                                    cp.ResetClicks(cp.getPlayer());
                                    RandomAfterTurn(state);
                                    CustomViewActivity.UPDATETURN(cp.getPlayer().getName());
                                    CustomViewActivity.UDATECLICKS(cp.getPlayer().getClicks());
                                }
                            }, 1000);


                        }

                    } else if (state[column][row] == STATES.FLIPPED) {

                        Toast.makeText(getContext(), "Invaild Move", Toast.LENGTH_LONG).show();
                    }

                    }
                    else {
                  

                        Toast.makeText(getContext(), "Game Over", Toast.LENGTH_LONG).show();
                        AlertDialog(p,cp);

                    }
                } else {
                    Toast.makeText(getContext(), "Invaild Touc", Toast.LENGTH_LONG).show();
                }

        }

        return true;
    }

    /**This method manages player by making instance of @class:PlayerControl &&  @class:Players,
     * && setting up board to desired 4 x 4 in this particular case.
     */
    public void PlayerManagement(){
        /**
         * @code
         *
         */
        cp = new PlayerControl();
        p = new Player(cp.getPlayer().getName());
        SetgameColumns(4);
        SetgameRows(4);

    }

    /**
     * @Additional-Feature: Toast number of randomly shuffled cards of same colour as of {@value: colour of first click.}
     * @param chancePaint
     * @return frequency
     */
    public int chances(Paint chancePaint){

        int frequency = 0;
        for (int i = 0; i < numColumns; i++) {

            for (int j = 0; j < numRows; j++) {

                if(colors[i][j].getColor()==chancePaint.getColor()){

                    frequency +=1;
                }

            }
        }
        return frequency-1;
      // return (frequency*100.0f)/16;

    }

    /**
     * This Method returns int value of one of 8 colours stored in xml file {@xml randomcolour}, random number picks one
     * colour out of resource files.
     * @return int
     */
    public int setrand(){

       final int u = ran.nextInt(+7);
        return rainbow[u];

    }

    /**
     * This Method checks two integers, basically getColor() values of two stored paints upon clicks
     * @param a
     * @param b
     * @return boolean
     */
    public boolean checkMatch(int a,int b){

        if(a==b|| b == a){

            return true;
        }
        return false;
    }

    /**
     * This Method checks if any FACEDOWN card is left, if is ture game continues. Otherwise, end game.
     * @param stateArray
     * @return boolean
     */
    public boolean gameEnd(STATES [][] stateArray){

        int count=0;

        for (int i = 0; i < numColumns; i++) {

            for (int j = 0; j < numRows; j++) {

                if(stateArray[i][j]== STATES.FACEDOWN) count +=1;
            }
        }
         return count > 0 ? false:true;
    }

    /**
     * This Method randomise colour of FACEDOWN.cards after turn is over.
     * @param stateArray
     */
    public void RandomAfterTurn(STATES [][] stateArray){

        for (int i = 0; i < numColumns; i++) {

            for (int j = 0; j < numRows; j++) {

                if(stateArray[i][j]==STATES.FACEDOWN) colors[i][j].setColor(setrand());
            }
        }

    }

    /**
     * Dialogue Box creation upon endgame to display winner and set new game.
     * @param p
     * @param cp
     */
    public void AlertDialog(Player p,PlayerControl cp){

        if(gameEnd(state)) {

            CustomViewActivity cva = (CustomViewActivity) this.getContext();
            cva.DialogBox(p,cp);
        }
    }




}