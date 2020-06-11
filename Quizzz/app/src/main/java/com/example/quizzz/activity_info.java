package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_info extends AppCompatActivity {

    private static final String TAG = "activity_info";
    private Button btnBack; // deklaracja przycisku
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnBack = (Button) findViewById(R.id.btnBackInfo);
        tvInfo = (TextView) findViewById(R.id.Information);

        tvInfo.setMovementMethod(new ScrollingMovementMethod()); //ustawienie scrollowania textview

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Back clicked");
               finish();
            }
        });
    }
}
