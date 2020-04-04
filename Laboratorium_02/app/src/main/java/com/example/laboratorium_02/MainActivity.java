package com.example.laboratorium_02;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    DecimalFormat Df = new DecimalFormat("###,###,###,###.00");

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    EditText txtPLN;
    EditText txtUSD;
    EditText txtEURO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPLN = (EditText)findViewById(R.id.txtPLN);
        txtUSD = (EditText)findViewById(R.id.txtUSD);
        txtEURO = (EditText)findViewById(R.id.txtEURO);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPLN.setText("");
                txtUSD.setText("");
                txtEURO.setText("");
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                 String plnstr = txtPLN.getText().toString();
                 double pln = Double.parseDouble(plnstr);
                 String usdstr = String.valueOf(Df.format(pln/4.24));
                 String eurostr = String.valueOf(Df.format(pln/4.58));
                 txtUSD.setText(usdstr);
                 txtEURO.setText(eurostr);
                } catch (NumberFormatException e) {
                    //ignorowanie bledow
                }
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String usdstr = txtUSD.getText().toString();
                    double usd = Double.parseDouble(usdstr);
                    String plnstr = String.valueOf(Df.format(usd*4.24));
                    String eurostr = String.valueOf(Df.format(usd*0.93));
                    txtPLN.setText(plnstr);
                    txtEURO.setText(eurostr);
                } catch (NumberFormatException e) {
                    //ignorowanie bledow
                }
            }
        });

        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String eurostr = txtEURO.getText().toString();
                    double euro = Double.parseDouble(eurostr);
                    String plnstr = String.valueOf(Df.format(euro*4.58));
                    String usdstr = String.valueOf(Df.format(euro*1.08));
                    txtPLN.setText(plnstr);
                    txtUSD.setText(usdstr);
                } catch (NumberFormatException e) {
                    //ignorowanie bledow
                }
            }
        });
    }
}