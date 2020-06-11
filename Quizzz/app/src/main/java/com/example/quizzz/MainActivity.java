package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity_of_questions = 8; //ilość pytań

    private static final String TAG = "activity_main";
   private Button btnStart, btnInfo, btnOptions, btnExit; // deklaracja przycisków


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (requestCode == 100) {
                Log.d(TAG, "ActivityResult : activity_options result");
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "ActivityResult : activity_options result OK");
                    Bundle BundleResult = data.getExtras();
                    quantity_of_questions= BundleResult.getInt("result");//zapisanie odebranych danych od activity_options
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "ActivityResult : activity_options result CANCELED");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Options clicked");
                openActivity_Options();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Info clicked");
                openActivity_Info();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Exit clicked");
                finish();
                System.exit(0);
            }
        });
    }

    public void openActivity_Options(){ //przejście do activity_options
        Intent intent = new Intent(MainActivity.this, activity_options.class);
        Bundle OptionsBundle = new Bundle();
        OptionsBundle.putInt("quantity_of_questions", quantity_of_questions);
        intent.putExtras(OptionsBundle);
        startActivityForResult(intent, 100);
        Log.d(TAG, "openActivity_Options : sending quantity of questions");
    }

    public void openActivity_Info(){ //przejście do activity_info
        Intent intent = new Intent(this, activity_info.class);
        startActivity(intent);
    }

}
