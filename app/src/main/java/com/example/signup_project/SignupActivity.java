package com.example.signup_project;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText nametxt, phonetxt, emailtxt, passwordtxt;
    MaterialButton signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        signupBtn.setOnClickListener(v ->{
            validateSignup();
        } );

    }

    public void validateSignup(){

        if (nametxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Name Is Null", Toast.LENGTH_SHORT).show();
        return;
        }

        if(phonetxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Phone is null", Toast.LENGTH_SHORT).show();
            return;
        }



    }
}