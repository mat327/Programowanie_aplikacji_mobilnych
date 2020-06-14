package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_addquestion extends AppCompatActivity {
    private static final String TAG = "activity_addquestion";

    ArrayList<question> questions_list = new ArrayList<>();
    question question = new question();

    private Button btnAdd, btnBackAddQuestion;
    private TextView etQuestion, etAnswer1, etAnswer2, etAnswer3, etAnswer4, etCorrectAnswer;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {//zapisanie stanu przed zmianą orientacji
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("questions_list", questions_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);

        btnAdd = (Button) findViewById(R.id.btnNewQuestion);
        btnBackAddQuestion =(Button) findViewById(R.id.btnBackAddQuestion);
        etQuestion = (TextView) findViewById(R.id.etQuestion);
        etAnswer1 = (TextView) findViewById(R.id.etAnswer1);
        etAnswer2 = (TextView) findViewById(R.id.etAnswer2);
        etAnswer3 = (TextView) findViewById(R.id.etAsnwer3);
        etAnswer4 = (TextView) findViewById(R.id.etAsnwer4);
        etCorrectAnswer = (TextView) findViewById(R.id.etCorrectAnswer);

        if(savedInstanceState != null) { //Przywracanie stanu przed obróceniem ekranu
            questions_list = (ArrayList<question>) savedInstanceState.getSerializable("questions_list");//przywrócenie questons_list
        }else {
            questions_list = GenerateListOfQuestions();//pobranie z bazy danych list pytań
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick : btn Add Question clicked");

                question.setId(1); //zapisanie do obiektu
                question.setQuestion(etQuestion.getText().toString());
                question.setAnswer1(etAnswer1.getText().toString());
                question.setAnswer2(etAnswer2.getText().toString());
                question.setAnswer3(etAnswer3.getText().toString());
                question.setAnswer4(etAnswer4.getText().toString());
                question.setCorrect_answer(etCorrectAnswer.getText().toString());

                if(question.getQuestion().equals("")
                        || question.getAnswer1().equals("")
                        || question.getAnswer2().equals("")
                        || question.getAnswer3().equals("")
                        || question.getAnswer4().equals("")
                        || question.getCorrect_answer().equals("")){
                    Toast.makeText(getApplicationContext(), "Wszytkie pola muszą być wypełnione.", Toast.LENGTH_LONG).show(); //puste pola
                }else if(!question.getCorrect_answer().equals(question.getAnswer1()) &&
                        !question.getCorrect_answer().equals(question.getAnswer2()) &&
                        !question.getCorrect_answer().equals(question.getAnswer3()) &&
                        !question.getCorrect_answer().equals(question.getAnswer4())){
                    Toast.makeText(getApplicationContext(), "Conajmniej jedna odpowiedź musi byc taka sama jak Poprawna odpowiedź.", Toast.LENGTH_LONG).show(); //poprawna odp = conajmniej jednej odpowiedzi
                }
                else if(QuestionAlreadyExist(questions_list, question)){
                    Toast.makeText(getApplicationContext(), "Takie pytanie już istnieje.", Toast.LENGTH_LONG).show(); //pytanie już istnieje
                }else{
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(activity_addquestion.this);
                    boolean Success = dataBaseHelper.addQuestion(question);
                    if (Success == true) {
                        Toast.makeText(getApplicationContext(), "Pytanie zostało dodane do bazy pytań.", Toast.LENGTH_LONG).show(); //informacja o dodaniu do db
                        etQuestion.setText("");
                        etAnswer1.setText("");
                        etAnswer2.setText("");
                        etAnswer3.setText("");
                        etAnswer4.setText("");
                        etCorrectAnswer.setText("");// czyszczenie pól tekstowych po dodaniu pytania
                        question= new question();// czyszczenie obiektu
                    } else {
                        Toast.makeText(getApplicationContext(), "Wystapił błąd przy dodawaniu pytania.", Toast.LENGTH_LONG).show(); //informacja o nie powodzeniu dodania do db
                    }
                }
            }
        });

        btnBackAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick : btn Exit clicked.");
                finish();
            }
        });
    }

    public ArrayList<question> GenerateListOfQuestions() { //funkja uzyskująca liste pytań z bazy danych
        DataBaseHelper dataBaseHelper = new DataBaseHelper(activity_addquestion.this);
        ArrayList<question> returnList = new ArrayList<>();
        returnList = dataBaseHelper.ReturnQuestionsList();

        return returnList;
    }

    public boolean QuestionAlreadyExist(List<question> questions_list, question new_question){//funkcja sprawdzająca czy istnieje juz takie samo pytanie
        for(int i = 0; i<questions_list.size(); i++){
            question temp_question = questions_list.get(i);
            if(temp_question.getQuestion().equals(new_question.getQuestion())){//Jeżeli pytania sa identyczne

                if(temp_question.getAnswer1().equals(new_question.getAnswer1()) || temp_question.getAnswer1().equals(new_question.getAnswer2()) ||
                temp_question.getAnswer1().equals(new_question.getAnswer3()) || temp_question.getAnswer1().equals(new_question.getAnswer4())){ //jeżeli odp 1 pasuje do jakiej kolwiek odp

                    if(temp_question.getAnswer2().equals(new_question.getAnswer1()) || temp_question.getAnswer2().equals(new_question.getAnswer2()) ||
                            temp_question.getAnswer2().equals(new_question.getAnswer3()) || temp_question.getAnswer2().equals(new_question.getAnswer4())){ //jeżeli odp 2 pasuje do jakiej kolwiek odp

                        if(temp_question.getAnswer3().equals(new_question.getAnswer1()) || temp_question.getAnswer3().equals(new_question.getAnswer2()) ||
                                temp_question.getAnswer3().equals(new_question.getAnswer3()) || temp_question.getAnswer3().equals(new_question.getAnswer4())){ //jeżeli odp 3 pasuje do jakiej kolwiek odp

                            if(temp_question.getAnswer4().equals(new_question.getAnswer1()) || temp_question.getAnswer4().equals(new_question.getAnswer2()) ||
                                    temp_question.getAnswer4().equals(new_question.getAnswer3()) || temp_question.getAnswer4().equals(new_question.getAnswer4())){ //jeżeli odp 4 pasuje do jakiej kolwiek odp
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
