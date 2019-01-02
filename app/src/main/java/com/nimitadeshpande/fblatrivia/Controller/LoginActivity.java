package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.nimitadeshpande.fblatrivia.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickSignIn(View view) {
        EditText emailText = (EditText)findViewById(R.id.EditText_email_id);
        String email = emailText.getText().toString();

        EditText passwordText = (EditText)findViewById(R.id.EditText_password_id);
        String password = passwordText.getText().toString();

        //Log.d("WEFOUNDIT", email + " " + password);
        if (isValidSignInCredentials(email, password)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            //display some error
        }

    }

    private boolean isValidSignInCredentials(String email, String password) {
        //check database stuff
        return false;
    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


}
