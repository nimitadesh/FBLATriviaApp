package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nimitadeshpande.fblatrivia.R;

import java.io.File;


public class CategoryActivity extends AppCompatActivity {

    private String selectedCategory;
    private String uid;
    private String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        selectedCategory = intent.getExtras().getString("selectedCategory");
        emailId = intent.getExtras().getString("emailId");

        Log.d("Message", "extra_message:" + selectedCategory);
        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);
        Log.d("Got category", "Category:" );
        setContentView(R.layout.activity_category);
    }

    public void onClickReview(View view) {
       FirebaseStorage storage = FirebaseStorage.getInstance();
       StorageReference gsReference = storage.getReferenceFromUrl("gs://fbla-trivia.appspot.com");
       Task task = gsReference.getFile(new File("Competitive Events Preparation Document.pdf"));

       task.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
               // Local temp file has been created
               Log.d("filedownload", "Downloaded file");
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {
               // Handle any errors
               Log.d("filedownloadfailed", "Download file failed");
           }
       });
//       Intent intent = new Intent(Intent.ACTION_VIEW);
//       intent.setDataAndType(path, "application/pdf");
//       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//       startActivity(intent);
    }


    public void onClickQuiz (View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("emailId", emailId);
        startActivity(intent);
    }

    public void onClickScores (View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("emailId", emailId);
        startActivity(intent);
    }
}
