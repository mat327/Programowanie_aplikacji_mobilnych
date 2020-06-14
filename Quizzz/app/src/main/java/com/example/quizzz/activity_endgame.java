package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class activity_endgame extends AppCompatActivity {
    private static final String TAG = "activity_endgame";

    int quantity_of_questions; //ilość pytań
    int player_points;//wynik gracza
    double percent=0;

    private Button btnStartAgain, btnBackEndGame;
    private TextView tvResults;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {//zapisanie stanu przed zmianą orientacji
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("quantity_of_questions", quantity_of_questions);
        savedInstanceState.putString("tvResults", tvResults.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        btnStartAgain = (Button) findViewById(R.id.btnStartAgain);
        btnBackEndGame = (Button) findViewById(R.id.btnBackEndGame);
        tvResults =(TextView) findViewById(R.id.tvResults);

        if(savedInstanceState != null) { //Przywracanie stanu przed obróceniem ekranu
        quantity_of_questions = savedInstanceState.getInt("quantity_of_questions");
        tvResults.setText(savedInstanceState.getString("tvResults"));
        }
        else {
            final Intent myLocalIntent = getIntent();
            final Bundle myBundle = myLocalIntent.getExtras();
            quantity_of_questions = myBundle.getInt("quantity_of_questions");
            player_points = myBundle.getInt("player_points");
            percent = player_points/(double)quantity_of_questions;

            if (percent < 0.25) {
                tvResults.setText("Otrzymałeś " + player_points + " z " + quantity_of_questions + " możliwych punktów.\n Musisz trochę poćwiczyć.");
            } else if (percent >= 0.25 && percent < 0.50) {
                tvResults.setText("Otrzymałeś " + player_points + " z " + quantity_of_questions + " możliwych punktów.\n Nie ma się czym chwalić.");
            } else if (percent >= 0.50 && percent < 0.75) {
                tvResults.setText("Otrzymałeś " + player_points + " z " + quantity_of_questions + " możliwych punktów.\n Dobry wynik.");
            } else if (percent >= 0.75 && percent < 1) {
                tvResults.setText("Otrzymałeś " + player_points + " z " + quantity_of_questions + " możliwych punktów.\n Bardzo dobry wynik.");
            } else if (percent == 1) {
                tvResults.setText("Otrzymałeś " + player_points + " z " + quantity_of_questions + " możliwych punktów.\n Gratulacje lepiej już się nie da.");
            }
        }

        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Start Again clicked");
                Intent intent = new Intent(activity_endgame.this, activity_game.class);
                Bundle ResultsBundle = new Bundle();
                ResultsBundle.putInt("quantity_of_questions", quantity_of_questions);
                intent.putExtras(ResultsBundle);
                startActivity(intent);
                Log.d(TAG, "openActivity_Game : sending quantity of questions");
                finish();
            }
        });

        btnBackEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Back clicked");
                finish();
            }
        });
    }
}
