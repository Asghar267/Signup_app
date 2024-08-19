package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    TextView regbtn;
    TextInputEditText emailtxt, passwordtxt;
    MaterialButton loginBtn;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
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
        auth.signInWithEmailAndPassword(emailtxt.getText().toString(), passwordtxt.getText().toString()).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                firestore.collection("users").document(authResult.getUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Toast.makeText(LoginActivity.this, documentSnapshot.getString("name") ,Toast.LENGTH_SHORT).show();

                        Intent logIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(logIntent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e.getMessage().contains("The password is invalid") || e.getMessage().contains("There is no user record")) {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}