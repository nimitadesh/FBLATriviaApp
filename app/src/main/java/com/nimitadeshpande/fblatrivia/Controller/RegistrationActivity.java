package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nimitadeshpande.fblatrivia.R;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onClickRegister(View view) {
        boolean isAllCorrect = true;
        EditText fnameText = (EditText)findViewById(R.id.fname_id);
        String fname = fnameText.getText().toString();
        if (fname.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            fnameText.setError("This field can not be blank");
        }

        EditText lnameText = (EditText)findViewById(R.id.lname_id);
        String lname = lnameText.getText().toString();
        if (lname.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            lnameText.setError("This field can not be blank");
        }

        EditText emailText = (EditText)findViewById(R.id.email_id);
        String email = emailText.getText().toString();
        if (email.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            emailText.setError("This field can not be blank");
        }

        EditText pwdText = (EditText)findViewById(R.id.password_id);
        String password = pwdText.getText().toString();
        if (password.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            pwdText.setError("This field can not be blank");
        }

        EditText confirmPwdText = (EditText)findViewById(R.id.confirmpwd_id);
        String confirmPwd = confirmPwdText.getText().toString();
        if (confirmPwd.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            confirmPwdText.setError("This field can not be blank");
        } else if (!confirmPwd.equals(password)) {
            isAllCorrect = false;
            confirmPwdText.setError("Passwords don't match");
        }

        if (isAllCorrect) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

}
