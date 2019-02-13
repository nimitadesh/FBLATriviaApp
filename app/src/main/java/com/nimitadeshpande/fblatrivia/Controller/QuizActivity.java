package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nimitadeshpande.fblatrivia.Model.QuizCategory;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimitadeshpande.fblatrivia.Model.Category;
import com.nimitadeshpande.fblatrivia.Model.QuizCategory;
import com.nimitadeshpande.fblatrivia.R;
import com.nimitadeshpande.fblatrivia.utils.FblaUtils;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbref = firebaseDatabase.getReference();
    private QuizCategory quizCategory;

    //2 textviews to hold the question and the scores
    private TextView scoreView;
    private TextView questionView;
    private String emailId;

    private Button buttonChoice1, buttonChoice2, buttonChoice3, buttonChoice4, quitButton;
    private boolean buttonChoice1Set = false;
    private boolean buttonChoice2Set = false;
    private boolean buttonChoice3Set = false;
    private boolean buttonChoice4Set = false;

    int score = 0;
    String answer = "";
    private int rand = 0;
    private int questionNumber = 1;
    private String category;


    DatabaseReference questionRef, rightAnswerRef, wrongAnswer1Ref, wrongAnswer2Ref, wrongAnswer3Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        scoreView = (TextView)findViewById(R.id.score);
        questionView = (TextView)findViewById(R.id.question);

        quizCategory = new QuizCategory();
        Intent intent = getIntent();
        category = intent.getExtras().getString("selectedCategory");
        emailId = intent.getExtras().getString("emailId");

        buttonChoice1 = (Button)findViewById(R.id.choice1);
        buttonChoice2 = (Button)findViewById(R.id.choice2);
        buttonChoice3 = (Button)findViewById(R.id.choice3);
        buttonChoice4 = (Button)findViewById(R.id.choice4);
        quitButton = (Button)findViewById(R.id.quit);
        Log.d("categoryMessage", category);
        Log.d("questionNumber", Integer.toString(questionNumber));
        updateQuestion();


        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonChoice1.getText().toString().toUpperCase().equals(answer)){
                    score+=1;
                    updateScore();
                }
                else {
                    displayRightAnswer();
                }
            }
        });

        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonChoice2.getText().toString().toUpperCase().equals(answer)){
                    score+=1;
                    updateScore();
                }
                else {
                    displayRightAnswer();
                }
            }
        });

        buttonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonChoice3.getText().toString().toUpperCase().equals(answer)){
                    score+=1;
                    updateScore();
                }
                else {
                    displayRightAnswer();
                }
            }
        });

        buttonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonChoice4.getText().toString().toUpperCase().equals(answer)){
                    score+=1;
                    updateScore();
                }
                else {
                    displayRightAnswer();
                }
            }
        });
    }

    public void reloadCategories(View view){

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("category", category);
        dbref.child("scores").child(FblaUtils.EncodeString(emailId)).child(quizCategory.getCategoryMap().get(category)).child("score").setValue(score);
        startActivity(intent);
        //setContentView(R.layout.activity_score);

    }

    public void updateQuestion(){

        if(questionNumber==11) {
            // go back to Score Activity
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("emailId", emailId);
            intent.putExtra("category", category);
            dbref.child("scores").child(FblaUtils.EncodeString(emailId)).child(quizCategory.getCategoryMap().get(category)).child("score").setValue(score);
            startActivity(intent);
        }

        int max = 4;
        int min = 1;
        int range = max - min + 1;

        for(int i=0; i<=4; i++){
            rand = (int)(Math.random()*range)+min;
        }
        //reinitialize the booleans to all buttons will be refreshed
        buttonChoice1Set = false;
        buttonChoice2Set = false;
        buttonChoice3Set = false;
        buttonChoice4Set = false;

        questionRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/questionText");
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                Log.d("dbQuestion", "question" + question);
                questionView.setText(question);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rightAnswerRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/rightAnswers/1");
        rightAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rightAnswer = dataSnapshot.getValue(String.class);
                Log.d("RIGHT ANSWER Q", "Question " + questionNumber);
                Log.d("RIGHT ANSWER", "Answer: " + rightAnswer);
                answer = rightAnswer.toUpperCase();
                switch(rand){
                    case 1:
                        buttonChoice1.setText(rightAnswer);
                        buttonChoice1Set = true;
                        break;
                    case 2:
                        buttonChoice2.setText(rightAnswer);
                        buttonChoice2Set = true;
                        break;
                    case 3:
                        buttonChoice3.setText(rightAnswer);
                        buttonChoice3Set = true;
                        break;
                    case 4:
                        buttonChoice4.setText(rightAnswer);
                        buttonChoice4Set = true;
                        break;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        wrongAnswer1Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/wrongAnswers/1");
        wrongAnswer1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wrongAnswer = dataSnapshot.getValue(String.class);
                setButtonText(wrongAnswer);
                Log.d("wrongAnswer1", wrongAnswer);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        wrongAnswer2Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/wrongAnswers/2");
        wrongAnswer2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wrongAnswer = dataSnapshot.getValue(String.class);
                setButtonText(wrongAnswer);
                Log.d("wrongAnswer2", wrongAnswer);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        wrongAnswer3Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/wrongAnswers/3");
        wrongAnswer3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wrongAnswer = dataSnapshot.getValue(String.class);
                setButtonText(wrongAnswer);
                Log.d("wrongAnswer3", wrongAnswer);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        questionNumber++;
    }

    private void setButtonText(String text){
        if(!buttonChoice1Set){
            buttonChoice1.setText(text);
            buttonChoice1Set = true;
        }
        else if(!buttonChoice2Set){
            buttonChoice2.setText(text);
            buttonChoice2Set = true;
        }
        else if(!buttonChoice3Set){
            buttonChoice3.setText(text);
            buttonChoice3Set = true;
        }
        else {
            buttonChoice4.setText(text);
            buttonChoice4Set = true;
        }

    }

    private void updateScore(){
        scoreView.setText(""+ score + "/" + (questionNumber-1));
        updateQuestion();
    }

    private void displayRightAnswer(){
        Toast toast = new Toast(QuizActivity.this);
        toast.setGravity(Gravity.TOP, 0, 0);

        toast.makeText(QuizActivity.this,
                "Incorrect! Right answer: " + answer, Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateScore();
            }
        }, 5000);
    }

}
