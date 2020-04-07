package com.example.laboratorium_02v2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //zmienna przechowująca wartość spinnera
    String spin1="";
    //nowy format
    DecimalFormat Df = new DecimalFormat("###,###,###,###.00");

    Button button1;
    Button button2;
    EditText txtValue1;
    EditText txtProc;
    EditText txtValue2;
    Spinner spinner1;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtValue1 = (EditText)findViewById(R.id.txtValue1);
        txtProc = (EditText)findViewById(R.id.txtProc);
        txtValue2 = (EditText)findViewById(R.id.txtValue2);
        txtValue2.setInputType(EditorInfo.TYPE_NULL);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        spinner1 = findViewById(R.id.spinner1);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        // Spinner Drop lista elemntów
        List<String> categories = new ArrayList<String>();
        categories.add("Tak");
        categories.add("Nie mam zdania");
        categories.add("Nie");

        // tworzenie adaptera dla spinnera
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        Log.i("Lab02 :", "Stworzono adapter dla spinnera");

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //przypisanie wartośći adaptera do spinnera
        spinner1.setAdapter(dataAdapter);
        Log.i("Lab02 :", "Przypisano adapter do spinnera");


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pobranie wartości z spinnera do zmiennej string
                spin1 = parent.getItemAtPosition(position).toString();
                Log.i("Lab02 :", "Wczytano wartość spinnera");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //czyszczenie
                txtValue1.setText("");
                txtProc.setText("");
                txtValue2.setText("");
                ratingBar.setRating(0);
                Log.i("Lab02 :", "Wyczyszczono pola");

            }
        });

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //pobranie wartości edittext
                    String str1 = txtValue1.getText().toString();
                    double value = Double.parseDouble(str1); //wartość zamówienia
                    String str2 = txtProc.getText().toString();
                    double proc = Double.parseDouble(str2);//procent napiwku
                    double service = ratingBar.getRating();//procent za obsługe
                    double sumproc; //suma wartości procentowych
                    Log.i("Lab02 :", "Wczytano poprawnie wszystkie wartości");

                    //sprawdzenie spinnerów w celu przeliczenia wartości
                    if(spin1=="Tak"){
                        sumproc = (proc+(service*0.5)+2.5)/100; //2.5 to procenty do napiwku za dobre jedzenie
                        double Tip = value*sumproc;
                        txtValue2.setText(String.valueOf(Df.format(Tip))+" zł");
                        Log.i("Lab02 :", "Wyświetlono wartość napiwku dla 'Tak'");
                    }

                    if(spin1=="Nie mam zdania"){
                        sumproc = (proc+(service*0.5))/100;
                        double Tip = value*sumproc;
                        txtValue2.setText(String.valueOf(Df.format(Tip))+" zł");
                        Log.i("Lab02 :", "Wyświetlono wartość napiwku dla 'Nie wiem'");
                    }

                    if(spin1=="Nie"){
                        sumproc = (proc+(service*0.5)-2.5)/100;
                        double Tip = value*sumproc;
                        txtValue2.setText(String.valueOf(Df.format(Tip))+" zł");
                        Log.i("Lab02 :", "Wyświetlono wartość napiwku dla 'Nie'");
                    }


                } catch (NumberFormatException e) {
                    //wyświetlanie blędu o nie podanej wartości
                    Toast.makeText(getApplicationContext(), "Musisz podać wszystkie wartości w polach, format wprowadzanych wartości 000.00",Toast.LENGTH_LONG).show();
                }
                }

        });

    }

}