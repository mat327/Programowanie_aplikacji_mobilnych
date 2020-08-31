package com.example.quizzz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    public static final String COLUMN_QUESTION = "QUESTION";
    public static final String QUESTIONS_TABLE = COLUMN_QUESTION + "S_TABLE";
    public static final String COLUMN_ANSWER_1 = "ANSWER1";
    public static final String COLUMN_ANSWER_2 = "ANSWER2";
    public static final String COLUMN_ANSWER_3 = "ANSWER3";
    public static final String COLUMN_ANSWER_4 = "ANSWER4";
    public static final String COLUMN_CORRECT_ANSWER = "CORRECT_ANSWER";
    public static final String COLUMN_ID = "ID";

    //konstruktor
    public DataBaseHelper(@Nullable Context context) {
        super(context, "questions.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "OnCreate");
    String createTableStatement = "CREATE TABLE " + QUESTIONS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION + " TEXT, " + COLUMN_ANSWER_1 + " TEXT, " + COLUMN_ANSWER_2 + " TEXT, " + COLUMN_ANSWER_3 + " TEXT, " + COLUMN_ANSWER_4 + " TEXT, " + COLUMN_CORRECT_ANSWER + " TEXT)";

    db.execSQL(createTableStatement);

        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (1, '2+2=?', '1', '2', '3', '4', '4')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (2, 'Nietoperz to ?', 'Ssak', 'Płaz', 'Gad', 'Ptak', 'Ssak')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (3, 'Jakim symbolem w matematyce oznaczamy wyskość', 'h', 'V', 'P', 'r', 'h')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (4, '10% z 30 to ?', '1', '3', '10', '6', '3')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (5, 'Żaba to ?', 'Płaz', 'Gad', 'Ssak', 'Ryba', 'Płaz')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (6, 'Borsuk jest ?', 'Mięsożerny', 'Roślinożerny', 'Wszystkożerny', 'Padlinożerny', 'Wszystkożerny')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (7, 'Stado dzików to ?', 'Rój', 'Chmara', 'Wataha', 'Gromada', 'Wataha')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (8, 'Gdzie mieszka Lis ?', 'W norze', 'W dziupli', 'W żereniach', 'Wszędzie', 'W norze')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (9, 'Średnica jest równa ?', '2*pi', '2*r', '3*r', '2*pi*r', '2*r')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (10, 'H2O to ?', 'woda', 'sól', 'dwutlenek węgla', 'kwas octowy', 'woda')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (11, 'Wzór na pole kwadratu to ?', 'a*h/2', '2*pi*r', 'a*a', 'a*h', 'a*a')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (12, '11^2 = ?', '121', '81', '144', '100', '121')");
        db.execSQL("INSERT INTO QUESTIONS_TABLE (ID, QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, CORRECT_ANSWER) VALUES (13, 'Jaki owoc ma nasiona na wierzchu ?', 'czereśnia', 'jabłko', 'truskawka', 'gruszka', 'truskawka')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "OnUpgrade");
    }

    public boolean addQuestion(question question){
        Log.d(TAG, "addQuestion");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION, question.getQuestion());
        cv.put(COLUMN_ANSWER_1, question.getAnswer1());
        cv.put(COLUMN_ANSWER_2, question.getAnswer2());
        cv.put(COLUMN_ANSWER_3, question.getAnswer3());
        cv.put(COLUMN_ANSWER_4, question.getAnswer4());
        cv.put(COLUMN_CORRECT_ANSWER, question.getCorrect_answer());

        long insert = db.insert(QUESTIONS_TABLE, null, cv); //insert = -1 w przypadku niepowodzenia
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public ArrayList<question> ReturnQuestionsList(){
        Log.d(TAG, "ReturnQuestionsList");
        ArrayList<question> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+QUESTIONS_TABLE; //zapytanie bd
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int questionID = cursor.getInt(0);
                String question = cursor.getString(1);
                String answer1 = cursor.getString(2);
                String answer2 = cursor.getString(3);
                String answer3 = cursor.getString(4);
                String answer4 = cursor.getString(5);
                String correct_answer = cursor.getString(6);
                question selectedQuestion = new question(questionID, question, answer1, answer2, answer3, answer4, correct_answer);
                returnList.add(selectedQuestion);
            }while(cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        db.close();
        return returnList;
    }

}
