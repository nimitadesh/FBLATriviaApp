package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nimitadeshpande.fblatrivia.R;

import org.w3c.dom.Text;

import java.io.File;


public class CategoryActivity extends AppCompatActivity {

    private String selectedCategory;
    private String emailId;
//    private TextView categoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the Intent that started this activity and extract the string for category
        //name and the emailId of the user
        Intent intent = getIntent();
        selectedCategory = intent.getExtras().getString("selectedCategory");
        emailId = intent.getExtras().getString("emailId");
//        categoryView = (TextView)findViewById(R.id.categoryId);
//        categoryView.setText("" + selectedCategory);
        //log messages for debugging
        Log.d("Message", "extra_message:" + selectedCategory);



        setContentView(R.layout.activity_category);
    }

    /**
     * Method to transition to QuizActivity to start the quiz
     */
    public void onClickQuiz (View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("emailId", emailId);
        startActivity(intent);
    }
    /**
     * Method to transition to ScoreActivity to display scores
     */
    public void onClickScores (View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("emailId", emailId);
        startActivity(intent);
    }
}
