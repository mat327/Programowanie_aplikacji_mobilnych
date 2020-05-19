package com.example.labratorium_04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button WysStrone, WysSms, Mapa, Wybierz, WysIndeks;
    EditText Numer, Tresc, Szerokosc, Dlugosc, Indeks, Strona;
    TextView wybranykontakt, odpowiedz;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if((requestCode==100) && (resultCode== Activity.RESULT_OK)){
                Uri kontaktUri = data.getData();
                Cursor kursor = getContentResolver().query(kontaktUri, null, null, null, null);
                if(kursor.moveToFirst() == true) {
                    int imie = kursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int numer = kursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    wybranykontakt.setText("Nazwa : " + kursor.getString(imie) + "\nNumer : " + kursor.getString(numer));
                }
                else{
                    Log.i("Lab04 :", " kursor.moveToFirst = false");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try{
            if((requestCode==200) && (resultCode== Activity.RESULT_OK)){
                Bundle BundleResult=data.getExtras();
                odpowiedz.setText("Odpowiedź :\n"+BundleResult.getString("result"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WysStrone = (Button)findViewById(R.id.WysStrone);
        WysSms = (Button)findViewById(R.id.WysSms);
        WysIndeks = (Button)findViewById(R.id.WysIndeks);
        Mapa = (Button)findViewById(R.id.Mapa);
        Wybierz = (Button)findViewById(R.id.Wybierz);
        Numer = (EditText)findViewById(R.id.Numer);
        Tresc = (EditText)findViewById(R.id.Tresc);
        Szerokosc = (EditText)findViewById(R.id.Szerokosc);
        Dlugosc = (EditText)findViewById(R.id.Dlugosc);
        Indeks = (EditText)findViewById(R.id.Indeks);
        Strona = (EditText)findViewById(R.id.Strona);
        wybranykontakt = (TextView)findViewById(R.id.textView5);
        odpowiedz = (TextView)findViewById(R.id.textView7);

        WysStrone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myActivity = new Intent(Intent.ACTION_VIEW);
                myActivity.setData(Uri.parse("http://"+Strona.getText().toString()));
                startActivity(myActivity);
                Log.i("Lab04 :", "Wyswietlanie strony WWW");
            }
        });

        WysSms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myActivity2 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+Numer.getText().toString()));
                myActivity2.putExtra("sms_body", Tresc.getText().toString() );
                startActivity(myActivity2);
                Log.i("Lab04 :", "Wysyłanie SMS");
            }
        });

        Mapa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myActivity3 = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+Szerokosc.getText().toString()+","+Dlugosc.getText().toString()+",20z"));
                startActivity(myActivity3);
                Log.i("Lab04 :", "Wyswietlanie mapy");
            }
        });

        Wybierz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myActivity4 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(myActivity4, 100);
                Log.i("Lab04 :", "Wybieranie kontaktu z listy");
            }
        });

        WysIndeks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myActivity5 = new Intent(MainActivity.this, StudentActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("indeks", Indeks.getText().toString());
                myActivity5.putExtras(myBundle);
                startActivityForResult(myActivity5, 200);
                Log.i("Lab04 :", "Wysyłanie indeksu do innego activity");
            }
        });

    }
}
