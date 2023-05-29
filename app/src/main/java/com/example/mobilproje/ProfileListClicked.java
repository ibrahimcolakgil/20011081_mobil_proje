package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class ProfileListClicked extends AppCompatActivity {

    private TextView email_et, phone_num_et, distance_et, time_et;

    private String from_email, to_email;

    private MaterialButton match_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;

    private String toUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list_clicked);

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        mReference = FirebaseDatabase.getInstance().getReference();

        if (mUser != null) {
            // Currently signed user's email.
            from_email = mUser.getEmail();
        } else {
            // No user is signed in
        }

        email_et = (TextView) findViewById(R.id.email_tv);
        phone_num_et = (TextView) findViewById(R.id.phone_number_tv);
        distance_et = (TextView) findViewById(R.id.distance_tv);
        time_et = (TextView) findViewById(R.id.time_tv);
        match_btn = (MaterialButton) findViewById(R.id.match_btn);

        Bundle bundle = getIntent().getExtras();

        email_et.setText(bundle.getString("email"));
        phone_num_et.setText(bundle.getString("phone_number"));
        distance_et.setText(bundle.getString("time"));
        time_et.setText(bundle.getString("distance"));

        to_email = email_et.getText().toString();

        ArrayList<Student> local_list = new ArrayList<Student>(MainActivity.global_user_list);

        for(Student index : MainActivity.global_user_list){
            if(Objects.equals(index.getEmail(), to_email)){
                toUid = index.getUid();
            }
        }

        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Match match = new Match(from_email, to_email);
                mReference.child("matches").child(toUid).setValue(match)
                        .addOnCompleteListener(ProfileListClicked.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ProfileListClicked.this, "Match request sent", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ProfileListClicked.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}