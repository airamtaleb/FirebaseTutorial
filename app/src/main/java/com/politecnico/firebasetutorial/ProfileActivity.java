package com.politecnico.firebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import static com.politecnico.firebasetutorial.R.id.btnCerrarSesion;

public class ProfileActivity extends AppCompatActivity {


    private Button mButtonSicgnOut, mButtonAcceder;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mButtonSicgnOut = (Button) findViewById(btnCerrarSesion);

        mButtonAcceder = findViewById(R.id.buttonAcceder);

        mButtonSicgnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();

            }
        });

        mButtonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                startActivity(new Intent(ProfileActivity.this, ListActivity.class));
                finish();

            }
        });



    }
}