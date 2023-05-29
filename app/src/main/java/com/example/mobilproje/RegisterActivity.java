package com.example.mobilproje;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, confirm_password;
    private MaterialButton register_button;

    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        email = (EditText)findViewById(R.id.email_et);
        password = (EditText)findViewById(R.id.password_et);
        confirm_password = (EditText)findViewById(R.id.confirm_password_et);

        register_button = (MaterialButton)findViewById(R.id.register_btn);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_s, password_s, confirm_password_s;

                email_s = email.getText().toString();
                password_s = password.getText().toString();
                confirm_password_s = confirm_password.getText().toString();

                if(!TextUtils.isEmpty(email_s) && !TextUtils.isEmpty(password_s) && !TextUtils.isEmpty(confirm_password_s)){
                    // If all fields are filled.
                    if(password_s.equals(confirm_password_s)){
                        // If password and confirm password is the same.
                        // Registration process should begin.

                        mAuth.createUserWithEmailAndPassword(email_s, password_s)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information.
                                            mUser = mAuth.getCurrentUser();

                                            Student student = new Student("", "", "", "", "", "", email_s, password_s, "", mUser.getUid());
                                            mReference.child("students").child(mUser.getUid()).setValue(student)
                                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(RegisterActivity.this, "Register is successful", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        // If password and confirm password is not the same.
                        Toast.makeText(RegisterActivity.this, "Password and Confirm Password fields must match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // If some of the fields are empty.
                    Toast.makeText(RegisterActivity.this, "Please Fill the Mandatory Fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView have_acc = findViewById(R.id.have_txt);
        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}
