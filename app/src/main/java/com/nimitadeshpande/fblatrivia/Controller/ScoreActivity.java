package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimitadeshpande.fblatrivia.Model.QuizCategory;
import com.nimitadeshpande.fblatrivia.R;
import com.nimitadeshpande.fblatrivia.utils.FblaUtils;

public class ScoreActivity extends AppCompatActivity {
    //create database reference
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbref = firebaseDatabase.getReference();
    private QuizCategory quizCategory;
    private String emailId;
    private String category;
    private TextView competitiveEventsScoreView;
    private TextView nationalOfficersScoreView;
    private TextView nationalSponsorsScoreView;
    private TextView regionsScoreView;
    private TextView historyScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        emailId = intent.getExtras().getString("emailId");
        category = intent.getExtras().getString("category");
        setContentView(R.layout.activity_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get scores for the five categories
        DatabaseReference competitiveEventsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/scores/" + FblaUtils.EncodeString(emailId) + "/competitiveEvents/score");
        DatabaseReference nationalOfficersRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/scores/" + FblaUtils.EncodeString(emailId) + "/nationalOfficers/score");
        DatabaseReference nationalSponsorsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/scores/" + FblaUtils.EncodeString(emailId) + "/nationalSponsors/score");
        DatabaseReference regionsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/scores/" + FblaUtils.EncodeString(emailId) + "/fblaRegions/score");
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fbla-trivia.firebaseio.com/scores/" + FblaUtils.EncodeString(emailId) + "/fblaHistory/score");

        competitiveEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                competitiveEventsScoreView = (TextView) findViewById(R.id.competitiveEventsScore);
                int score = dataSnapshot.getValue(Integer.class);
                competitiveEventsScoreView.setText(Integer.toString(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nationalOfficersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nationalOfficersScoreView = (TextView) findViewById(R.id.nationalOfficersScore);
                int score = dataSnapshot.getValue(Integer.class);
                nationalOfficersScoreView.setText(Integer.toString(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nationalSponsorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nationalSponsorsScoreView = (TextView) findViewById(R.id.sponsorsScore);
                int score = dataSnapshot.getValue(Integer.class);
                nationalSponsorsScoreView.setText(Integer.toString(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        regionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                regionsScoreView = (TextView) findViewById(R.id.regionsScore);
                int score = dataSnapshot.getValue(Integer.class);
                regionsScoreView.setText(Integer.toString(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyScoreView = (TextView) findViewById(R.id.historyScore);
                int score = dataSnapshot.getValue(Integer.class);
                historyScoreView.setText(Integer.toString(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void loadCategoryPage(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void doLogout(View view) {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d("TAG1","onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d("TAG2", "onAuthStateChanged:signed_out");
                startActivity(new Intent(ScoreActivity.this, LoginActivity.class));
            }
            // ...
        }
    };
}

}
