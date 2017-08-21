package com.tcgogogo.flipgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    public static final int N = 4;
    public static final int TOT = N * N;
    private static final String RED = "#ff0000";
    private static final String BLUE = "#0000ff";
    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {1, 0, -1, 0};

    private TextView mHint;
    private MyButton[] btnList = new MyButton[16];
    private int[] btnId = new int[16];

    private String rColor(String color) {
        return color.equals(RED) ? BLUE : RED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnId[0] = R.id.btn0;   btnId[1] = R.id.btn1;
        btnId[2] = R.id.btn2;   btnId[3] = R.id.btn3;
        btnId[4] = R.id.btn4;   btnId[5] = R.id.btn5;
        btnId[6] = R.id.btn6;   btnId[7] = R.id.btn7;
        btnId[8] = R.id.btn8;   btnId[9] = R.id.btn9;
        btnId[10] = R.id.btn10;   btnId[11] = R.id.btn11;
        btnId[12] = R.id.btn12;   btnId[13] = R.id.btn13;
        btnId[14] = R.id.btn14;   btnId[15] = R.id.btn15;
        mHint = (TextView) findViewById(R.id.hint);
        generateGame();
        for (int i = 0; i < TOT; i ++) {
            btnList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyButton button = (MyButton) view;
                    refreshUI(button);
                    int step = canWin();
                    if(step <= 0) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("结果")
                                .setMessage(step == 0 ? "游戏胜利" : "游戏失败")
                                .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mHint.setText("");
                                        generateGame();
                                    }
                                })
                                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).show();
                    } else {
                        mHint.setText(String.format(getResources().getString(R.string.hint), step));
                    }
                }
            });
        }
    }

    public void generateGame() {
        Random random = new Random();
        do {
            for (int i = 0; i < TOT; i ++) {
                btnList[i] = (MyButton) findViewById(btnId[i]);
                String color = random.nextInt(2) % 2 == 0 ? RED : BLUE;
                btnList[i].setBackgroundColor(Color.parseColor(color));
                btnList[i].setColor(color);
                btnList[i].setId(i);
            }
        } while(canWin() == -1);
    }

    public int canWin() {
        int state = 0;
        for (int i = 0; i < TOT; i ++) {
            state <<= 1;
            if (btnList[i].getColor().equals(BLUE)) {
                state += 1;
            }
        }
        return GameHelper.canWin(state);
    }

    public void refreshUI(MyButton button) {
        int id = button.getId();
        String chgColor = rColor(button.getColor());
        button.setColor(chgColor);
        button.setBackgroundColor(Color.parseColor(chgColor));
        int x = id / N;
        int y = id % N;
        for (int i = 0; i < 4; i ++) {
            int xx = x + dx[i];
            int yy = y + dy[i];
            if (xx >= 0 && yy >= 0 && xx < N && yy < N) {
                int chgId = xx * N + yy;
                chgColor = rColor(btnList[chgId].getColor());
                btnList[chgId].setColor(chgColor);
                btnList[chgId].setBackgroundColor(Color.parseColor(chgColor));
            }
        }
    }
}
