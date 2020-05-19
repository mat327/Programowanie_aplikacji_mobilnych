package com.example.labratorium_04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {
Button WysDane;
TextView Indeks2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        WysDane = (Button)findViewById(R.id.WysDane);
        Indeks2 = (TextView) findViewById(R.id.Indeks2);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();

        String result = "Nie znaleziono studenta";

        if(myBundle.getString("indeks").equals("235475")){
            result = "Mateusz Białek";
        }

        if(myBundle.getString("indeks").equals("123456")){
            result = "Jan Kowalski";
        }

        Indeks2.setText("Otrzymany indeks studenta : "+myBundle.getString("indeks"));
        myBundle.putString("result", result);
        myLocalIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myLocalIntent);

        WysDane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

}

}
