package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference database;

    public static ArrayList<Student> global_user_list = new ArrayList<Student>();// List for all students in the firebase database.
    public static ArrayList<Match> global_match_list = new ArrayList<Match>();// List for all matches in the firebase database.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference("students");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get all data in students node.
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Student student = dataSnapshot.getValue(Student.class);
                    global_user_list.add(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database = FirebaseDatabase.getInstance().getReference("matches");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get all data in students node.
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Match match = dataSnapshot.getValue(Match.class);
                    global_match_list.add(match);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        // Button handler for profile page.
        MaterialButton profile_btn = findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        // Button handler for profile list page.
        MaterialButton profile_list_btn = findViewById(R.id.profile_list_btn);
        profile_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileListActivity.class));
            }
        });

        // Button handler for map page.
        MaterialButton map_btn = findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        // Button handler for match page.
        MaterialButton match_btn = findViewById(R.id.match_btn);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MatchActivity.class));
            }
        });
    }
}