package com.example.vbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


public class Login extends AppCompatActivity implements View.OnClickListener  {

    private TextView others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        others = (TextView) findViewById(R.id.others);
        others.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.others:
                startActivity(new Intent(this, Register.class));

                break;
        }
    }
}
