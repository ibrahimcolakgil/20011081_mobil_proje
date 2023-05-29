package com.example.mobilproje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileListActivity extends AppCompatActivity implements StudentAdapter.buttonClickListener {

    private RecyclerView mRecyclerView;
    DatabaseReference database;
    StudentAdapter sAdapter;
    ArrayList<Student> list;

    String filter_time_s, filter_distance_s;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_list_page);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        database = FirebaseDatabase.getInstance().getReference("students");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        sAdapter = new StudentAdapter(this, list, this);
        mRecyclerView.setAdapter(sAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get all data in students node.
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Student student = dataSnapshot.getValue(Student.class);
                    list.add(student);
                }
                sAdapter.notifyDataSetChanged();

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
                startActivity(new Intent(ProfileListActivity.this, ProfileActivity.class));
            }
        });

        // Button handler for profile list page.
        MaterialButton profile_list_btn = findViewById(R.id.profile_list_btn);
        profile_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileListActivity.this, ProfileListActivity.class));
            }
        });

        // Button handler for map page.
        MaterialButton map_btn = findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileListActivity.this, MapActivity.class));
            }
        });

        // Button handler for match page.
        MaterialButton match_btn = findViewById(R.id.match_btn);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileListActivity.this, MatchActivity.class));
            }
        });
    }
    // When clicked match button, send information to the profile list clicked activity.
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ProfileListActivity.this, ProfileListClicked.class);

        Bundle bundle = new Bundle();

        bundle.putString("email", list.get(position).getEmail());
        bundle.putString("phone_number", list.get(position).getPhone_number());
        bundle.putString("time", list.get(position).getShare_time());
        bundle.putString("distance", list.get(position).getDistance_from_campus());

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
