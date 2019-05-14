package com.august.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DisplayGame1P extends AppCompatActivity {
    private List<Button> buttons;
    private List<Integer> buttons2;
    private TextView player;
    private TextView victory;
    private boolean isAI = false;
    private List<Button> pressed;
    int currentPlayer = 1;
    int plays = 1;
    private static final int[] BUTTON_IDS = {
            R.id.r1c1,
            R.id.r1c2,
            R.id.r1c3,
            R.id.r2c1,
            R.id.r2c2,
            R.id.r2c3,
            R.id.r3c1,
            R.id.r3c2,
            R.id.r3c3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game);
        buttons = new ArrayList<Button>();
        pressed = new ArrayList<Button>();
        for (int id : BUTTON_IDS) {
            Button button = (Button) findViewById(id);
            buttons.add(button);
        }
        victory = (TextView) findViewById(R.id.victory);
    }


    public void Button_Retry(View v) {
        super.recreate();
    }

    public void Button_Click(View v) {
        int ID = v.getId();
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        boolean valid = false;
        int buttonId = v.getId();
        pressed.add(b);
        if (buttonText != "X" && buttonText != "O") {
            if (currentPlayer == 1) {
                b.setText("X");
            } else {
                b.setText("O");
            }
            valid = true;
        }
        int score = checkVictory();
        if (score < 4) {
            TextView plyr = (TextView) findViewById(R.id.player);
            plyr.setVisibility(View.INVISIBLE);
            TextView current = (TextView) findViewById(R.id.current);
            Button retry = (Button) findViewById(R.id.retry);
            current.setVisibility(View.INVISIBLE);
            for (int id : BUTTON_IDS) {
                Button button = (Button) findViewById(id);
                button.setEnabled(false);
            }
            retry.setVisibility(View.VISIBLE);
        } else {
            Switch(valid);
        }
    }

    private Button play;
    Handler handler = new Handler();

    public void Switch(boolean valid) {
        TextView t = (TextView) findViewById(R.id.player);
        if (valid == true) {

            if (currentPlayer == 1) {
                Log.d("cpu", "reached");
                currentPlayer++;
                t.setText("CPU");
                t.setTextColor(Color.BLUE);
                for (int id : BUTTON_IDS) {
                    Button button = (Button) findViewById(id);
                    button.setEnabled(false);
                }
                do {
                    int random = new Random().nextInt((9 - 1) + 1) + 1;
                    play = buttons.get(random - 1);
                    Log.d("button", String.valueOf(random - 1));
                } while (pressed.contains(play));
                Log.d("button", String.valueOf(play.getId()));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Button_Click(play);
                        for (int id : BUTTON_IDS) {
                            Button button = (Button) findViewById(id);
                            button.setEnabled(true);
                        }
                    }
                }, 2500);   //5 seconds

            } else {
                currentPlayer--;
                t.setText("PLAYER ONE");
                t.setTextColor(Color.RED);
            }
            plays++;
        }
    }

    public boolean checkRows() {
        char a, b, c;
        int color;
        for (int i = 0; i < 7; i += 3) {
            a = buttons.get(i).getText().charAt(0);
            b = buttons.get(i + 1).getText().charAt(0);
            c = buttons.get(i + 2).getText().charAt(0);
            if (checkThree(a, b, c) == true) {
                if (currentPlayer == 1) {
                    color = Color.RED;
                } else {
                    color = Color.BLUE;
                }
                buttons.get(i).setTextColor(color);
                buttons.get(i + 1).setTextColor(color);
                buttons.get(i + 2).setTextColor(color);
                return true;
            }
        }
        return false;
    }

    public boolean checkCols() {
        char a, b, c;
        int color;
        for (int i = 0; i < 3; i++) {
            Log.d("sq", buttons.get(i + 3).getText().toString());
            a = buttons.get(i).getText().charAt(0);
            b = buttons.get(i + 3).getText().charAt(0);
            c = buttons.get(i + 6).getText().charAt(0);
            if (checkThree(a, b, c) == true) {
                if (currentPlayer == 1) {
                    color = Color.RED;
                } else {
                    color = Color.BLUE;
                }
                buttons.get(i).setTextColor(color);
                buttons.get(i + 3).setTextColor(color);
                buttons.get(i + 6).setTextColor(color);
                return true;
            }
        }
        return false;
    }

    public boolean checkDiag() {
        char a, b, c;
        int color;
        a = buttons.get(0).getText().charAt(0);
        b = buttons.get(4).getText().charAt(0);
        c = buttons.get(8).getText().charAt(0);
        if (currentPlayer == 1) {
            color = Color.RED;
        } else {
            color = Color.BLUE;
        }
        if (checkThree(a, b, c) == true) {

            buttons.get(0).setTextColor(color);
            buttons.get(4).setTextColor(color);
            buttons.get(8).setTextColor(color);
            return true;
        } else {
            a = buttons.get(2).getText().charAt(0);
            b = buttons.get(4).getText().charAt(0);
            c = buttons.get(6).getText().charAt(0);
            if (checkThree(a, b, c) == true) {
                buttons.get(2).setTextColor(color);
                buttons.get(4).setTextColor(color);
                buttons.get(6).setTextColor(color);
                return true;
            }
        }
        return false;
    }

    public boolean checkThree(char a, char b, char c) {
        return ((a != '_') && (a == b) && (b == c));
    }

    public void check() {
        //Log.d("lag", Boolean.toString(checkCols()));
        boolean b = checkCols();
        boolean c = checkRows();
        if (b == true || c == true) {
            victory.setText("Text");
        }
    }

    public int check_Winner(int currentPlayer) {
        String message = "";
        if (currentPlayer == 1) {
            message = "WINNER: X";
            victory.setText(message);
            return 1;
        } else
            message = "WINNER: O";
        victory.setText(message);
        return 2;
    }

    public int checkVictory() {
        String message;
        if (checkRows() == true) {
            return check_Winner(currentPlayer);
        } else if (checkCols() == true) {
            return check_Winner(currentPlayer);
        } else if (checkDiag() == true) {
            return check_Winner(currentPlayer);
        } else if (plays == 9) {
            message = " It's a tie!";
            victory.setText(message);
            int color;

            for (int id : BUTTON_IDS) {
                Button button = (Button) findViewById(id);
                button.setTextColor(Color.GREEN);
            }
            return 3;
        } else
            return 4;
    }
}
