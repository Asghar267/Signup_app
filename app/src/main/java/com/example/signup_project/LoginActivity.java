package com.example.signup_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextView regbtn;
    TextInputEditText emailtxt, passwordtxt;
    MaterialButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        regbtn = this.findViewById(R.id.reg_id);
        emailtxt = this.findViewById(R.id.email_id);
        passwordtxt = this.findViewById(R.id.password_id);
        loginBtn = findViewById(R.id.login_id);

        regbtn.setOnClickListener(v -> {
            Toast.makeText(this, "Button is Clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            validateLogin();
        });
    }

        public void validateLogin(){
            if (emailtxt.getText().toString().isEmpty()){
                Toast.makeText(this, "Email is required!",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(emailtxt.getText().toString()).matches()){
                Toast.makeText(this, "Email is not Valid", Toast.LENGTH_SHORT).show();
                return;

            }

            if(passwordtxt.getText().toString().isEmpty()){
                Toast.makeText(this, "Password is Required!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        }



}