package com.example.danishali2875170.Assignment02;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.danishali2875170.example28.R;
/**
 * This is a model class Activity at welcome screen
 *
 * @see android.app.Activity
 * @author Danish Ali
 * @studentNumber 2875170
 * @since 2018-11-02
 * @version 1.0
 */

public class MainActvity extends Activity {

    static String et_player1;
    static String et_player2;
    private EditText et_player01;
    private EditText et_player02;


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
        setContentView(R.layout.activity_main);


        Button play_start = (Button) findViewById(R.id.play_start);

        play_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_player01 = (EditText) findViewById(R.id.playerone_et);
                et_player1 = "" + et_player01.getText().toString();


                et_player02 = (EditText) findViewById(R.id.playertwo_et);
                et_player2 = et_player02.getText().toString();


                Intent passover = new Intent(MainActvity.this, CustomViewActivity.class);
                passover.putExtra("Player01passover", et_player01.getText().toString());
                passover.putExtra("Player02passover", et_player02.getText().toString());
                startActivityForResult(passover, 16);


            }
        });

    }
}
