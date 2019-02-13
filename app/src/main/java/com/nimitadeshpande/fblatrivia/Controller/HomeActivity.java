package com.nimitadeshpande.fblatrivia.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimitadeshpande.fblatrivia.Model.Category;
import com.nimitadeshpande.fblatrivia.R;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class HomeActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("category");
    public String selectedCategory = null;
    public String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        emailId = intent.getExtras().getString("emailId");
        Log.d("emailId", "emailId:" + emailId);
        setContentView(R.layout.activity_home);
        Spinner mySpinner = (Spinner) findViewById(R.id.categorySpinner);
        mySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, Category.values()));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                selectedCategory = Category.values()[position].toString();
                Log.d("SelectedCategory", "Category:" + selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("itemSelected", "Item Selected" + selectedCategory );
    }

    public void onClickQuizHome(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("emailId", emailId);
        startActivity(intent);
    }
}
