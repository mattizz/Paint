package com.example.mateusz.paint;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button buttonRed;
    public Button buttonGreen;
    public Button buttonBlue;
    public Button buttonYellow;
    public Button buttonClear;

    private AreaPainting areaPainting;

    public static int paintColor = Color.RED;
    public static boolean clear = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBlue = (Button) findViewById(R.id.btnBlue);
        buttonRed = (Button) findViewById(R.id.btnRed);
        buttonGreen = (Button) findViewById(R.id.btnGreen);
        buttonYellow = (Button) findViewById(R.id.btnYellow);
        buttonClear = (Button) findViewById(R.id.btnClear);

        buttonBlue.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        buttonGreen.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        buttonRed.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        buttonYellow.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);

        setListeners();

        areaPainting = new AreaPainting(MainActivity.this, null);
    }

    private void setListeners() {
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintColor = Color.BLUE;
            }
        });

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintColor = Color.RED;
            }
        });

        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintColor = Color.GREEN;
            }
        });

        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintColor = Color.YELLOW;
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public static void setClear() {
        clear = false;
    }
}
