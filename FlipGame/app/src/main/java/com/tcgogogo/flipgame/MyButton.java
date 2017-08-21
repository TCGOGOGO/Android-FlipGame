package com.tcgogogo.flipgame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tcgogogo on 17/8/21.
 */
public class MyButton extends Button {

    private String Color;
    private int Id;

    public MyButton(Context context, String color, int id) {
        super(context);
        this.Color = color;
        this.Id = id;
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
