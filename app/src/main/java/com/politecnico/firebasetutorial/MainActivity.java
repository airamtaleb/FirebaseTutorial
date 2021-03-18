package com.politecnico.firebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText mEditTextNombre;
    private EditText mEditTextCorreo;
    private EditText mEditTextPass;


    private Button mbtnCrearDatos;
    private Button mbtnSendToLogin;

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    private String name = "";
    private String correo = "";
    private String pass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEditTextNombre = (EditText) findViewById(R.id.editTextNombre);
        mEditTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        mEditTextPass = (EditText) findViewById(R.id.editTextPass);


        mbtnCrearDatos = (Button) findViewById(R.id.btnRegistro);
        mbtnSendToLogin = (Button) findViewById(R.id.btnSendtoLogin);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mbtnCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = mEditTextNombre.getText().toString();
                correo = mEditTextCorreo.getText().toString();
                pass = mEditTextPass.getText().toString();


                //si esta completo
                if (!name.isEmpty() && !correo.isEmpty() && !pass.isEmpty()) {
                    //pass al menos 6
                    if (pass.length() >= 6) {
                        registerUser();
                    } else {
                        Toast.makeText(MainActivity.this, "El pass debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        mbtnSendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //finish();  para que pueda volver

            }
        });


    }


    private void registerUser() {

        mAuth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();

                    map.put("name", name);
                    map.put("email", correo);
                    map.put("password", pass);


                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                finish();

                            } else {

                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {

                    Toast.makeText(MainActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /// mantiene la sesion  abierta
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();
        }

    }
}