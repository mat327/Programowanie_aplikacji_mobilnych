package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class activity_options extends AppCompatActivity {

    int quantity = 8; //wartość podstawowa ilości pytań
    private static final String TAG = "activity_options";
    private Button btnBack;
    private TextView quantity_of_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btnBack = (Button) findViewById(R.id.btnBackOptions);
        quantity_of_questions = (TextView) findViewById(R.id.quantity_of_questions);

        final Intent myLocalIntent = getIntent();
        final Bundle myBundle = myLocalIntent.getExtras();

        quantity_of_questions.setText(String.valueOf(myBundle.getInt("quantity_of_questions")));// ustawienie tekstu na wartość odebraną od activity_main

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Back clicked");

                try {
                    quantity = Integer.parseInt(quantity_of_questions.getText().toString()); // zapisanie do zmiennej ilości pytań
                    if(quantity<1 || quantity>10){ //warunek sprawdzający czy podana liczba jest w wymaganym przedziale
                        Toast.makeText(getApplicationContext(), "Ilość pytań musi zawierać się w przedziale od 1 do 10.", Toast.LENGTH_LONG).show(); //informacja o złej wartości
                    }
                    else{ //przejście do activity_main
                        myBundle.putInt("result", quantity);
                        myLocalIntent.putExtras(myBundle);
                        setResult(Activity.RESULT_OK, myLocalIntent);
                        finish();
                    }
                } catch(NumberFormatException nfe) {
                    Log.d(TAG, "Warning : can not save quantity of questions to int");
                    Toast.makeText(getApplicationContext(), "Ilość pytań musi być liczbą całkowitą.", Toast.LENGTH_LONG).show(); //informacja o wprowadzeniu znaków
                }
            }
        });
    }
}
