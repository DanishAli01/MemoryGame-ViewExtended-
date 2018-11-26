package com.example.danishali2875170.Assignment02;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.danishali2875170.example28.R;
/**
 * This is a model class Activity with custom View with board && Score Board.
 *
 * @see android.app.Activity
 * @author Danish Ali
 * @studentNumber 2875170
 * @since 2018-11-02
 * @version 1.0
 */


public class CustomViewActivity extends Activity {
    private Intent passover;

    static Button Player01;
    static Button Player02;
    static Button Player01_score;
    static Button Player02_score;
    static Button turn_player;
    static ImageButton smileys_match;
    static Button Clicks;
    static TextView fegtvs2;
    static TextView fegtvs1;
    static TextView fegtvp2;
    static TextView fegtvp1;
    private CustomView CV;


    @Override
    /**
     * Layout Setting & programatically controlling of xml elements
     *
     * OnCreate- Activity initilization
     * @param <code> Bundle </code>
     * @return void
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view);

        passover = getIntent();

        Player01 = (Button) findViewById(R.id.Player01);
        Player02 = (Button) findViewById(R.id.Player02);
        turn_player = (Button) findViewById(R.id.turn_player);
        smileys_match = (ImageButton) findViewById(R.id.smileys_match);
        Clicks = (Button) findViewById(R.id.Clicks);


        Player01.setText(getIntent().getStringExtra("Player01passover"));
        Player02.setText(getIntent().getStringExtra("Player02passover"));

        Player01_score = (Button) findViewById(R.id.Player01_score);
        Player02_score = (Button) findViewById(R.id.Player02_score);


        fegtvs2 = (TextView) findViewById(R.id.fegtvs2);
        fegtvs1 = (TextView) findViewById(R.id.fegtvs1);
        fegtvp2 = (TextView) findViewById(R.id.fegtvp2);
        fegtvp1 = (TextView) findViewById(R.id.fegtvp1);

        CV = (CustomView) findViewById(R.id.board);

    }

    /**
     * Update scoreboard in View from CustomView.java
     * @param playerName
     * @param s
     */
    static void UPDATESCOREBOARD(String playerName, int s) {

        if (playerName.equals(Player01.getText().toString())) {

            Player01_score.setText(s + " ");
        }
        if (playerName.equals(Player02.getText().toString())) {

            Player02_score.setText(s + " ");
        }
    }

    /**
     * Update Turn Viewholder from CustomView.java
     * @param playerName
     */
    static void UPDATETURN(String playerName) {

        turn_player.setText("Turn : " + playerName);
    }

    /**
     * Update Smileys View Holder from CustomView.java
     * @param match
     */
    static void UPDATESMILEY(Boolean match) {

        if (match) smileys_match.setImageResource(R.drawable.smiling);
        else smileys_match.setImageResource(R.drawable.sad);
    }

    /**
     * Updates clicke view holder using CustomView.java
     * @param clicks
     */
    static void UDATECLICKS(int clicks) {

        Clicks.setText("Clicks In Hand : " + clicks);
    }

    /**
     * Method to create dialog box upon game ending delcaring winner and Play again Option.
     * @param p
     * @param cp
     */
    public void DialogBox(Player p,PlayerControl cp) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CV.getContext());
        builder.setTitle("Game Finished");

        int p1score = cp.getPlayer().getScore();
        String p1name = cp.getPlayer().getName()+"";

        cp.SwitchPlayer();


        int p2score = cp.getPlayer().getScore();
        String p2name = cp.getPlayer().getName()+"";

        if(p1score>p2score) builder.setMessage(p1name +" is " + "Winner" );
        else if (p2score>p1score) builder.setMessage(p2name +" is " + "Winner" );
        else builder.setMessage("Tie" );


        builder.setPositiveButton("New game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent passover = new Intent(CustomViewActivity.this,MainActvity.class);
                startActivity(passover);
            }
        });

        builder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
