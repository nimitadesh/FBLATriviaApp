package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimitadeshpande.fblatrivia.R;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference loginRef;
    private FirebaseAuth auth;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickSignIn(View view) {
        EditText usernameText = (EditText)findViewById(R.id.username);
        String username = usernameText.getText().toString();

        EditText passwordText = (EditText)findViewById(R.id.password);
        String password = passwordText.getText().toString();

        Log.d("WEFOUNDIT", username + " " + password);
        if (isValidSignInCredentials(username, password)) {
            Log.d("worked", "It worked");
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        } else {
            //display some error
        }

    }

    private boolean isValidSignInCredentials(String username, String password) {
        //check database stuff
        final String pwd = password;
        if(Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            Log.d("matched", "email matched");
            performLogin(username, password);
        }
        else {
            dbref.child("user").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot != null) {
                        String userId = dataSnapshot.getValue(String.class);
                        performLogin(userId, pwd);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        return false;
    }

    private void performLogin(final String emailId, String password) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if(task.isSuccessful()){
                    Log.d("completedLogIn_unique", "Login complete");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("emailId", emailId);
                    startActivity(intent);
                    finish();
                //}
//                else{
//                    Toast toast = new Toast(LoginActivity.this);
//                    toast.setGravity(Gravity.TOP, 0, 0);
//                    toast.makeText(LoginActivity.this,
//                            "New User! Please Register", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LoginFailed", e.getMessage());
            }
        });

    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


}
