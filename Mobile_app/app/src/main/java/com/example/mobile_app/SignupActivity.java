package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText nametxt, phonetxt, emailtxt, passwordtxt;
    MaterialButton signupBtn;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nametxt = this.findViewById(R.id.name_id);
        phonetxt = this.findViewById(R.id.phone_id);
        emailtxt = this.findViewById(R.id.email_id);
        passwordtxt = this.findViewById(R.id.password_id);
        signupBtn = this.findViewById(R.id.signupbtn_id);

        signupBtn.setOnClickListener(v ->{
            validateSignup();
        });

    }

    public void validateSignup(){

        String name = nametxt.getText().toString();
        String phone = phonetxt.getText().toString();
        String email = emailtxt.getText().toString();
        String password = passwordtxt.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(this, "Name Is Null", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.isEmpty()){
            Toast.makeText(this, "Phone is null", Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(this, "Email is null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email is Not Valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(this, "Validate Success", Toast.LENGTH_SHORT).show();
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", authResult.getUser().getUid());
                map.put("name", name);
                map.put("phone", phone);
                map.put("email", email);

//                firestore.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
              ////  both work but below is for to get user id
                firestore.collection("users").document(authResult.getUser().getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(SignupActivity.this, "User is Created", Toast.LENGTH_SHORT).show();
                        Intent sinIntent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(sinIntent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

                return;

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("SignupActivity", "Signup failed", e);
                Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e("SignupActivity", "User creation failed", e);
//
//                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        });

    }
}