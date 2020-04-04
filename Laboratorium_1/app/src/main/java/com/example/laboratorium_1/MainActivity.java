package com.example.laboratorium_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button btnExit;
    private Button btnRed;
    private Button btnBlue;
    private Button btnGreen;
    private TextView txtSpy;
    private LinearLayout Screen;
    private Bundle outState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //przypisanie do obiektow nazw
        btnRed = (Button) findViewById(R.id.button1);
        btnBlue = (Button) findViewById(R.id.button2);
        btnGreen = (Button) findViewById(R.id.button3);
        btnExit = (Button) findViewById(R.id.exitbutton);
        txtSpy = (TextView)findViewById(R.id.txtSpy);
        Screen = (LinearLayout)findViewById(R.id.screen);



        btnRed.setOnClickListener(new OnClickListener() { //czekanie na klikniecie przycisku
            @Override
            public void onClick(View v) {
                Screen.setBackgroundColor(Color.RED);
                txtSpy.setText("RED"); //zmiana tekstu w spybox;
            }
        });

        btnBlue.setOnClickListener(new OnClickListener() { //czekanie na klikniecie przycisku
            @Override
            public void onClick(View v) {
                Screen.setBackgroundColor(Color.BLUE);
                txtSpy.setText("BLUE"); //zmiana tekstu w spybox;
            }
        });

        btnGreen.setOnClickListener(new OnClickListener() { //czekanie na klikniecie przycisku
            @Override
            public void onClick(View v) {
                Screen.setBackgroundColor(Color.GREEN);
                txtSpy.setText("GREEN"); //zmiana tekstu w spybox;
            }
        });

        btnExit.setOnClickListener(new OnClickListener() { //czekanie na klikniecie przycisku
            @Override
            public void onClick(View v) {
                finish(); //zakonczenie programu
            }
        });

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstance", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstance", Toast.LENGTH_SHORT).show();
    }
}
