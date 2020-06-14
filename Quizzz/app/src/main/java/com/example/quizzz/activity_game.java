package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class activity_game extends AppCompatActivity {

    private static final String TAG = "activity_game";

    int quantity_of_questions; //ilość pytań
    int number_of_question = 1; //obecne pytanie
    boolean isChecked = false;

    ArrayList<question> questions_list = new ArrayList<>();
    question random_question = new question();
    List<String> answers_list = new ArrayList<>();
    String player_answer = "";
    int player_points = 0;

    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnCheck, btnNext;
    private TextView question, Number_Of_Question;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {//zapisanie stanu przed zmianą orientacji
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("quantity_of_questions", quantity_of_questions);
        savedInstanceState.putInt("number_of_question", number_of_question);
        savedInstanceState.putInt("player_points", player_points);
        savedInstanceState.putString("player_answer", player_answer);
        savedInstanceState.putBoolean("isChecked", isChecked);
        savedInstanceState.putString("btnAnswer1", btnAnswer1.getText().toString());
        savedInstanceState.putString("btnAnswer2", btnAnswer2.getText().toString());
        savedInstanceState.putString("btnAnswer3", btnAnswer3.getText().toString());
        savedInstanceState.putString("btnAnswer4", btnAnswer4.getText().toString());
        savedInstanceState.putString("btnNext", btnNext.getText().toString());
        savedInstanceState.putString("Number_Of_Question", Number_Of_Question.getText().toString());

        savedInstanceState.putInt("random_question_id", random_question.getId());
        savedInstanceState.putString("random_question_question", random_question.getQuestion());
        savedInstanceState.putString("random_question_answer1", random_question.getAnswer1());
        savedInstanceState.putString("random_question_answer2", random_question.getAnswer2());
        savedInstanceState.putString("random_question_answer3", random_question.getAnswer3());
        savedInstanceState.putString("random_question_answer4", random_question.getAnswer4());
        savedInstanceState.putString("random_question_correct_question", random_question.getCorrect_answer());
        savedInstanceState.putSerializable("questions_list", questions_list);
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        btnAnswer1 = (Button) findViewById(R.id.btnAnswer1);
        btnAnswer2 = (Button) findViewById(R.id.btnAnswer2);
        btnAnswer3 = (Button) findViewById(R.id.btnAnswer3);
        btnAnswer4 = (Button) findViewById(R.id.btnAnswer4);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnNext = (Button) findViewById(R.id.btnNext);
        question = (TextView) findViewById(R.id.question);
        Number_Of_Question = (TextView) findViewById(R.id.number_of_question);

        final Intent myLocalIntent = getIntent();
        final Bundle myBundle = myLocalIntent.getExtras();
        quantity_of_questions = myBundle.getInt("quantity_of_questions");


        if(savedInstanceState != null){ //Przywracanie stanu przed obróceniem ekranu
            quantity_of_questions = savedInstanceState.getInt("quantity_of_questions");
            number_of_question = savedInstanceState.getInt("number_of_question");
            player_points = savedInstanceState.getInt("player_points");
            player_answer = savedInstanceState.getString("player_answer");
            isChecked = savedInstanceState.getBoolean("isChecked");
            btnAnswer1.setText(savedInstanceState.getString("btnAnswer1"));
            btnAnswer2.setText(savedInstanceState.getString("btnAnswer2"));
            btnAnswer3.setText(savedInstanceState.getString("btnAnswer3"));
            btnAnswer4.setText(savedInstanceState.getString("btnAnswer4"));
            btnNext.setText(savedInstanceState.getString("btnNext"));
            Number_Of_Question.setText(savedInstanceState.getString("Number_Of_Question"));

            questions_list = (ArrayList<question>) savedInstanceState.getSerializable("questions_list");//przywrócenie questons_list

            random_question.setAnswer1(savedInstanceState.getString("random_question_answer1"));//przywrócenie obiektu random_question
            random_question.setAnswer2(savedInstanceState.getString("random_question_answer2"));
            random_question.setAnswer3(savedInstanceState.getString("random_question_answer3"));
            random_question.setAnswer4(savedInstanceState.getString("random_question_answer4"));
            random_question.setQuestion(savedInstanceState.getString("random_question_question"));
            random_question.setCorrect_answer(savedInstanceState.getString("random_question_correct_question"));
            random_question.setId(savedInstanceState.getInt("random_question_id"));

            question.setText(random_question.getQuestion()); //ustawienie wyswietlania tersci pytania

            if (player_answer.equals(btnAnswer1.getText().toString())) { //przywrócenie wyglądy wybranej niezatwierdzonej odp
                btnAnswer1.setBackgroundColor(Color.CYAN);
            } else if (player_answer.equals(btnAnswer2.getText().toString())) {
                btnAnswer2.setBackgroundColor(Color.CYAN);
            } else if (player_answer.equals(btnAnswer3.getText().toString())) {
                btnAnswer3.setBackgroundColor(Color.CYAN);
            } else if(player_answer.equals(btnAnswer4.getText().toString())){
                btnAnswer4.setBackgroundColor(Color.CYAN);
            }

            if(isChecked == true){ //przywrócenie wyglądu przyciskó jeśli pyt było sprawdzone
                btnAnswer1.setEnabled(false);//zablokowanie przycisków po sprawdzeniu
                btnAnswer2.setEnabled(false);
                btnAnswer3.setEnabled(false);
                btnAnswer4.setEnabled(false);
                btnCheck.setEnabled(false);
                if (player_answer.equals(random_question.getCorrect_answer())){
                    HighLightButtons(true);
                } else {
                    HighLightButtons(false);
                }

            }

        }else{
            questions_list = GenerateListOfQuestions();
            random_question = GenerateRandomQuestion();
            answers_list.add(random_question.getAnswer1());
            answers_list.add(random_question.getAnswer2());
            answers_list.add(random_question.getAnswer3());
            answers_list.add(random_question.getAnswer4());
            NextQuestion();
        }


       /* question question_model = new question(1, "2+2=?", "2", "3", "4", "1", "4");
        boolean success= dataBaseHelper.addQuestion(question_model);
        Toast.makeText(getApplicationContext(), "Success= "+success, Toast.LENGTH_LONG).show();*/


        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Answer1 clicked");
                btnAnswer1.setBackgroundColor(Color.CYAN);
                btnAnswer2.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer3.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer4.setBackgroundResource(android.R.drawable.btn_default);
                player_answer = btnAnswer1.getText().toString();
            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Answer2 clicked");
                btnAnswer2.setBackgroundColor(Color.CYAN);
                btnAnswer1.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer3.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer4.setBackgroundResource(android.R.drawable.btn_default);
                player_answer = btnAnswer2.getText().toString();
            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Answer3 clicked");
                btnAnswer3.setBackgroundColor(Color.CYAN);
                btnAnswer2.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer1.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer4.setBackgroundResource(android.R.drawable.btn_default);
                player_answer = btnAnswer3.getText().toString();
            }
        });

        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Answer4 clicked");
                btnAnswer4.setBackgroundColor(Color.CYAN);
                btnAnswer2.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer3.setBackgroundResource(android.R.drawable.btn_default);
                btnAnswer1.setBackgroundResource(android.R.drawable.btn_default);
                player_answer = btnAnswer4.getText().toString();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Next clicked");
                if(isChecked == true) {
                    number_of_question++;
                    random_question = GenerateRandomQuestion();
                    answers_list.add(random_question.getAnswer1());//zapisanie odpowiedzi do listy
                    answers_list.add(random_question.getAnswer2());
                    answers_list.add(random_question.getAnswer3());
                    answers_list.add(random_question.getAnswer4());
                    btnAnswer3.setBackgroundResource(android.R.drawable.btn_default);//ustawienie przycisków na podstawowy wyglad
                    btnAnswer2.setBackgroundResource(android.R.drawable.btn_default);
                    btnAnswer1.setBackgroundResource(android.R.drawable.btn_default);
                    btnAnswer4.setBackgroundResource(android.R.drawable.btn_default);
                    btnAnswer1.setEnabled(true);//odblokowanie przycisków
                    btnAnswer2.setEnabled(true);
                    btnAnswer3.setEnabled(true);
                    btnAnswer4.setEnabled(true);
                    btnCheck.setEnabled(true);
                    isChecked=false;//ustawienie zmiennych ponownie na domyslne
                    player_answer="";
                    NextQuestion();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Nie sprawdziłeś odpowiedzi.", Toast.LENGTH_LONG).show(); //informacja o nie sprawdzeniu odp
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : button Check clicked");
                if (player_answer == "") {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś żandej odpowiedzi.", Toast.LENGTH_LONG).show(); //informacja o nie wybraniu odp
                } else {
                    btnAnswer1.setEnabled(false);//zablokowanie przycisków po sprawdzeniu
                    btnAnswer2.setEnabled(false);
                    btnAnswer3.setEnabled(false);
                    btnAnswer4.setEnabled(false);
                    btnCheck.setEnabled(false);
                    isChecked = true;
                    if (player_answer.equals(random_question.getCorrect_answer())) {
                        player_points++;
                        HighLightButtons(true);
                    } else {
                        HighLightButtons(false);
                    }
                }

            }
        });
    }

    public ArrayList<question> GenerateListOfQuestions() { //funkja uzyskująca liste pytań z bazy danych
        DataBaseHelper dataBaseHelper = new DataBaseHelper(activity_game.this);
        ArrayList<question> returnList = new ArrayList<>();
        returnList = dataBaseHelper.ReturnQuestionsList();

        return returnList;
    }

    public question GenerateRandomQuestion() {//funkcja losujaca pytanie z listy
        question RandomQuestion = new question();
        Random rand = new Random();
        int upperbound = questions_list.size();

        //generowanie randomowego indeksu z listy
        int int_random = rand.nextInt(upperbound);
        //zapisywanie losowego obiektu z listy
        RandomQuestion = questions_list.get(int_random);
        questions_list.remove(int_random); //usuwanie wybranego pytania z listy w celu unikniecia powtórzeń

        return RandomQuestion;
    }

    public String GenerateRandomAnswer() {//funkcja losujaca odpowiedzi z listy
        Random rand = new Random();
        int upperbound = answers_list.size();

        //generowanie randomowego indeksu z listy
        int int_random = rand.nextInt(upperbound);
        //zapisywanie losowego obiektu z listy
        String RandomAnswer = answers_list.get(int_random);
        answers_list.remove(int_random); //usuwanie wybranej odp z listy w celu unikniecia powtórzeń

        return RandomAnswer;
    }

    public void NextQuestion() {

        if (number_of_question == quantity_of_questions + 1) {//warunek kończący grę
            Intent intent = new Intent(activity_game.this, activity_endgame.class);
            Bundle ResultsBundle = new Bundle();
            ResultsBundle.putInt("quantity_of_questions", quantity_of_questions);
            ResultsBundle.putInt("player_points", player_points);
            intent.putExtras(ResultsBundle);
            startActivity(intent);
            Log.d(TAG, "openActivity_EndGame : sending quantity of questions, player points");
            finish();
        } else {
            question.setText(random_question.getQuestion());
            btnAnswer1.setText(GenerateRandomAnswer());
            btnAnswer2.setText(GenerateRandomAnswer());
            btnAnswer3.setText(GenerateRandomAnswer());
            btnAnswer4.setText(GenerateRandomAnswer());
            Number_Of_Question.setText(number_of_question + " z " + quantity_of_questions);

            if (number_of_question == quantity_of_questions) {
                btnNext.setText("Zakończ");
            }
        }
    }

    public void HighLightButtons(boolean correct) {
        btnAnswer3.setBackgroundResource(android.R.drawable.btn_default);
        btnAnswer2.setBackgroundResource(android.R.drawable.btn_default);
        btnAnswer1.setBackgroundResource(android.R.drawable.btn_default);
        btnAnswer4.setBackgroundResource(android.R.drawable.btn_default);
        if (correct == true) {
            if (random_question.getCorrect_answer().equals(btnAnswer1.getText().toString())) {
                btnAnswer1.setBackgroundColor(Color.GREEN);
            } else if (random_question.getCorrect_answer().equals(btnAnswer2.getText().toString())) {
                btnAnswer2.setBackgroundColor(Color.GREEN);
            } else if (random_question.getCorrect_answer().equals(btnAnswer3.getText().toString())) {
                btnAnswer3.setBackgroundColor(Color.GREEN);
            } else if(random_question.getCorrect_answer().equals(btnAnswer4.getText().toString())){
                btnAnswer4.setBackgroundColor(Color.GREEN);
            }

        } else {
            if (random_question.getCorrect_answer().equals(btnAnswer1.getText().toString())) {
                btnAnswer1.setBackgroundColor(Color.GREEN);
            } else if (random_question.getCorrect_answer().equals(btnAnswer2.getText().toString())) {
                btnAnswer2.setBackgroundColor(Color.GREEN);
            } else if (random_question.getCorrect_answer().equals(btnAnswer3.getText().toString())) {
                btnAnswer3.setBackgroundColor(Color.GREEN);
            } else if(random_question.getCorrect_answer().equals(btnAnswer4.getText().toString())){
                btnAnswer4.setBackgroundColor(Color.GREEN);
            }

            if (player_answer.equals(btnAnswer1.getText().toString())) {
                btnAnswer1.setBackgroundColor(Color.rgb(130,0,0));
            } else if (player_answer.equals(btnAnswer2.getText().toString())) {
                btnAnswer2.setBackgroundColor(Color.rgb(130,0,0));
            } else if (player_answer.equals(btnAnswer3.getText().toString())) {
                btnAnswer3.setBackgroundColor(Color.rgb(130,0,0));
            } else if(player_answer.equals(btnAnswer4.getText().toString())){
                btnAnswer4.setBackgroundColor(Color.rgb(130,0,0));
            }

        }
    }
}
