package com.example.mobilproje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private String email_s, password_s;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private MaterialButton login_btn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_login);

        email = (EditText)findViewById(R.id.email_et);
        password = (EditText)findViewById(R.id.password_et);

        login_btn = (MaterialButton)findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();

        TextView dont_have_acc = findViewById(R.id.dont_txt);
        dont_have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    public void LogIn(View v){
        email_s = email.getText().toString();
        password_s = password.getText().toString();

        if(!TextUtils.isEmpty(email_s) && !TextUtils.isEmpty(password_s)){
            mAuth.signInWithEmailAndPassword(email_s, password_s)
                    .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent switchActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(switchActivityIntent);
                        }
                    }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this, "Please Fill the Mandatory Fields!", Toast.LENGTH_SHORT).show();
        }

    }
}
