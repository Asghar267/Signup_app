package com.example.mobile_app;

import android.os.Bundle;
import android.util.Patterns;
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
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        nametxt = this.findViewById(R.id.name);
        phonetxt = this.findViewById(R.id.phone);
        emailtxt = this.findViewById(R.id.email_id);
        passwordtxt = this.findViewById(R.id.password_id);

        signupBtn = this.findViewById(R.id.signup_btn);

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

        if(emailtxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Email is null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt.getText().toString()).matches()){
            Toast.makeText(this, "Email is Not Valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwordtxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}