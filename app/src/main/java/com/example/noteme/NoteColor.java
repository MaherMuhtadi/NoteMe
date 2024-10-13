package com.example.noteme;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

public class NoteColor {

    private final String YELLOW;
    private final String PINK;
    private final String GREEN;
    public static String color;

    public NoteColor(Context context, LinearLayout container, View yellow_square, View pink_square, View green_square) {
        YELLOW = getHexColor(context, R.color.yellow);
        PINK = getHexColor(context, R.color.pink);
        GREEN = getHexColor(context, R.color.green);

        // Default color
        toggleSquare(YELLOW, yellow_square, pink_square, green_square, container);

        // Set click listeners for each square
        yellow_square.setOnClickListener(v -> toggleSquare(YELLOW, yellow_square, pink_square, green_square, container));
        pink_square.setOnClickListener(v -> toggleSquare(PINK, pink_square, yellow_square, green_square, container));
        green_square.setOnClickListener(v -> toggleSquare(GREEN, green_square, yellow_square, pink_square, container));
    }

    private String getHexColor(Context context, int colorRes) {
        int colorInt = ContextCompat.getColor(context, colorRes);
        return String.format("#%06X", (0xFFFFFF & colorInt));
    }

    private void toggleSquare(String selectedColor, View selectedSquare, View otherSquare1, View otherSquare2, LinearLayout container){
        color = selectedColor;
        container.setBackgroundColor(Color.parseColor(selectedColor));
        selectedSquare.setScaleX(0.5F);
        selectedSquare.setScaleY(0.5F);
        otherSquare1.setScaleX(1);
        otherSquare1.setScaleY(1);
        otherSquare2.setScaleX(1);
        otherSquare2.setScaleY(1);
    }
}