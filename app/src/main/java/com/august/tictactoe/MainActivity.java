package com.august.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //public int sessionID = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Start_Game(View view) {
        Intent intent = new Intent(this, DisplayGame.class);

        startActivity(intent);
    }
    public void Start_Game1P(View view) {
        Intent intent = new Intent(this, DisplayGame1P.class);

        startActivity(intent);
    }
}
