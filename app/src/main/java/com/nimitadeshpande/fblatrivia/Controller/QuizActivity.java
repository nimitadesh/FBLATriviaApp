package com.nimitadeshpande.fblatrivia.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nimitadeshpande.fblatrivia.Model.QuizCategory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimitadeshpande.fblatrivia.R;
import com.nimitadeshpande.fblatrivia.utils.FblaUtils;


public class QuizActivity extends AppCompatActivity {
    //Create FirebaseDatabase Reference
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbref = firebaseDatabase.getReference();

    //Create private variable of type QuizCategory
    private QuizCategory quizCategory;

    //2 textviews to hold the question and the scores
    private TextView scoreView;
    private TextView questionView;

    private String emailId;

    //buttons for answer choices
    private Button buttonChoice1, buttonChoice2, buttonChoice3, buttonChoice4, quitButton;
    private boolean buttonChoice1Set = false;
    private boolean buttonChoice2Set = false;
    private boolean buttonChoice3Set = false;
    private boolean buttonChoice4Set = false;

    //local variables
    int score = 0;
    String answer = "";
    private int rand = 0;
    private int questionNumber = 1;
    private String category;

    //Database references to access the questions and answers
    DatabaseReference questionRef, rightAnswerRef, wrongAnswer1Ref, wrongAnswer2Ref, wrongAnswer3Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //get the category and user emailId sent in from previous activity
        Intent intent = getIntent();
        category = intent.getExtras().getString("selectedCategory");
        emailId = intent.getExtras().getString("emailId");

        quizCategory = new QuizCategory();
        //link variables to UI elements
        scoreView = (TextView)findViewById(R.id.score);
        questionView = (TextView)findViewById(R.id.question);
        buttonChoice1 = (Button)findViewById(R.id.choice1);
        buttonChoice2 = (Button)findViewById(R.id.choice2);
        buttonChoice3 = (Button)findViewById(R.id.choice3);
        buttonChoice4 = (Button)findViewById(R.id.choice4);
        quitButton = (Button)findViewById(R.id.quit);

        //Logging messages for debugging
        Log.d("categoryMessage", category);
        Log.d("questionNumber", Integer.toString(questionNumber));
        //Initialize the UI with first question for the category
        updateQuestion();

        //Create listeners for the answer buttons to detect user's choice of answer
        //for a question. If the choice is the right answer, update the score and
        //bring up the next question-answers. If wrong answer, create a toast message
        //to display the right answer
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

    //Method to return to the category selection page
    public void reloadCategories(View view){
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("category", category);
        dbref.child("scores").child(FblaUtils.EncodeString(emailId)).child(quizCategory.getCategoryMap().get(category)).child("score").setValue(score);
        startActivity(intent);
    }

    public void updateQuestion(){

        // go to Score Activity of 10 questions have been answered
        if(questionNumber==11) {
            dbref.child("scores").child(FblaUtils.EncodeString(emailId)).child(quizCategory.getCategoryMap().get(category)).child("score").setValue(score);
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("emailId", emailId);
            intent.putExtra("category", category);
            startActivity(intent);
        }
        //set up a random number between 1-4 to display the right answer choice on buttons 1-4
        int max = 4;
        int min = 1;
        int range = max - min + 1;

        for(int i=0; i<=4; i++){
            rand = (int)(Math.random()*range)+min;
        }
        //reinitialize the booleans so all buttons will be refreshed
        buttonChoice1Set = false;
        buttonChoice2Set = false;
        buttonChoice3Set = false;
        buttonChoice4Set = false;

        //get the question and display it on the UI textview
        questionRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/questionText");
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                questionView.setText(question);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //get the right answer and display it on the UI button using the random number. This is
        //to ensure that the right answer is displayed randomly on one of the 4 buttons
        rightAnswerRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/rightAnswers/1");
        rightAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rightAnswer = dataSnapshot.getValue(String.class);
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
        //display the 3 wrong answer choices on the remaining 3 buttons
        wrongAnswer1Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/category/"+ quizCategory.getCategoryMap().get(category) +"/question"+ questionNumber +"/wrongAnswers/1");
        wrongAnswer1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wrongAnswer = dataSnapshot.getValue(String.class);
                setButtonText(wrongAnswer);
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //go onto the next question
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
    //method to update the score
    private void updateScore(){
        scoreView.setText(""+ score + "/" + (questionNumber-1));
        updateQuestion();
    }
    //method to display the right answer and move to the next question
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
