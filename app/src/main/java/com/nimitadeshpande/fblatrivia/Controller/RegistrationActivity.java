package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nimitadeshpande.fblatrivia.Model.User;
import com.nimitadeshpande.fblatrivia.R;
import com.nimitadeshpande.fblatrivia.utils.FblaUtils;

import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
            fnameText.setError("This field cannot be blank");
        }

        EditText lnameText = (EditText)findViewById(R.id.lname_id);
        String lname = lnameText.getText().toString();
        if (lname.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            lnameText.setError("This field cannot be blank");
        }

        EditText usernameText = (EditText)findViewById(R.id.username_id);
        String username = usernameText.getText().toString();
        if (username.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            usernameText.setError("This field cannot be blank");
        }

        EditText emailText = (EditText)findViewById(R.id.email_id);
        String email = emailText.getText().toString();
        if (email.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            emailText.setError("This field cannot be blank");
        }

        EditText pwdText = (EditText)findViewById(R.id.password_id);
        String password = pwdText.getText().toString();
        if (password.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            pwdText.setError("This field cannot be blank");
        }

        EditText confirmPwdText = (EditText)findViewById(R.id.confirmpwd_id);
        String confirmPwd = confirmPwdText.getText().toString();
        if (confirmPwd.trim().equalsIgnoreCase("")) {
            isAllCorrect = false;
            confirmPwdText.setError("This field cannot be blank");
        } else if (!confirmPwd.equals(password)) {
            isAllCorrect = false;
            confirmPwdText.setError("Passwords don't match");
        }

        if (isAllCorrect) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            //save user in firebase
            User user = new User(fname, lname, username, email, password);

            mDatabase.child("user").child(username).setValue(user);
            mDatabase.child("scores").child(FblaUtils.EncodeString(email)).child("competitiveEvents").child("score").setValue(0);
            mDatabase.child("scores").child(FblaUtils.EncodeString(email)).child("nationalOfficers").child("score").setValue(0);
            mDatabase.child("scores").child(FblaUtils.EncodeString(email)).child("nationalSponsors").child("score").setValue(0);
            mDatabase.child("scores").child(FblaUtils.EncodeString(email)).child("fblaHistory").child("score").setValue(0);
            mDatabase.child("scores").child(FblaUtils.EncodeString(email)).child("fblaRegions").child("score").setValue(0);

            if(TextUtils.isEmpty(email)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
            }

            if(password.length()<6){
                Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            }

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}
