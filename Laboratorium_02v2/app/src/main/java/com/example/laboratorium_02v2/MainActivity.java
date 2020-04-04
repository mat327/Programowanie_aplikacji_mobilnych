package com.example.laboratorium_02v2;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //zmienne przechowujące wartości spinnerów
    String spin1="";
    String spin2="";
    //zmienne do przeliczania walut
    private final double PLN2USD = 0.24;
    private final double PLN2EURO = 0.22;
    private final double USD2PLN = 4.25;
    private final double USD2EURO = 0.93;
    private final double EURO2PLN = 4.59;
    private final double EURO2USD = 1.08;
    //nowy format
    DecimalFormat Df = new DecimalFormat("###,###,###,##0.00");

    Button button1;
    Button button2;
    EditText txtValue1;
    EditText txtValue2;
    Spinner spinner1;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtValue1 = (EditText)findViewById(R.id.txtValue1);
        txtValue2 = (EditText)findViewById(R.id.txtValue2);
        txtValue2.setInputType(EditorInfo.TYPE_NULL);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        // Spinner Drop lista elemntów
        List<String> categories = new ArrayList<String>();
        categories.add("PLN");
        categories.add("USD");
        categories.add("EURO");

        // tworzenie adaptera dla spinnera
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //przypisanie wartośći adaptera do spinnerów
        spinner1.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pobranie wartości z spinnera do zmiennej string
                spin1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pobranie wartości z spinnera do zmiennej string
                spin2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //czyszczenie edittext
                txtValue1.setText("");
                txtValue2.setText("");

            }
        });

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //pobranie wartości edittext
                    String str1 = txtValue1.getText().toString();
                    double value = Double.parseDouble(str1);

                    //wyświetlanie błędu o tej samej walucie
                    if(spin1 == spin2){
                        Toast.makeText(getApplicationContext(), "Waluty nie mogą być takie same.",Toast.LENGTH_LONG).show();
                    }
                    //porównie spinnerów w celu przeliczenia wartości
                    if(spin1=="PLN"){
                        if(spin2=="USD"){
                            //obliczenie i wypisanie przeliczonej wartości do edittext
                            String str2 = String.valueOf(Df.format(value*PLN2USD));
                            txtValue2.setText(str2);
                        }
                        if(spin2=="EURO"){
                            String str2 = String.valueOf(Df.format(value*PLN2EURO));
                            txtValue2.setText(str2);
                        }
                    }

                    if(spin1=="USD"){
                        if(spin2=="PLN"){
                            String str2 = String.valueOf(Df.format(value*USD2PLN));
                            txtValue2.setText(str2);
                        }
                        if(spin2=="EURO"){
                            String str2 = String.valueOf(Df.format(value*USD2EURO));
                            txtValue2.setText(str2);
                        }
                    }

                    if(spin1=="EURO"){
                        if(spin2=="PLN"){
                            String str2 = String.valueOf(Df.format(value*EURO2PLN));
                            txtValue2.setText(str2);
                        }
                        if(spin2=="USD"){
                            String str2 = String.valueOf(Df.format(value*EURO2USD));
                            txtValue2.setText(str2);
                        }
                    }


                } catch (NumberFormatException e) {
                    //wyświetlanie blędu o nie podanej wartości
                    Toast.makeText(getApplicationContext(), "Musisz podać wartośc do przeliczenia.",Toast.LENGTH_LONG).show();
                }
                }

        });

    }

}