package com.example.vbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView textView, registerUser;
    private EditText editTextFullName, editTextPhone, editTextPassword, editTextEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        textView= (TextView) findViewById(R.id.createText);
        textView.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerBtn);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextPhone = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createText:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.registerBtn:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String Email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("ismi gir");
            editTextFullName.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextPhone.setError("inumara gir");
            editTextPhone.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            editTextEmail.setError("mail gir");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("dogru email gir");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("dogru parrolar gir");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("minumum 6 karakterden buyuk olamsı gerek");
            editTextPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(Email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            User user = new User(fullName, phone, Email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "basarili", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(Register.this, "basarisiz", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "basarisiz dene terakr", Toast.LENGTH_LONG).show();
                        }

                    }
                });











    }
}