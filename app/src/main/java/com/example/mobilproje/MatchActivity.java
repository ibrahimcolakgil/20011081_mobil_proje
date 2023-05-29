package com.example.mobilproje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MatchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    DatabaseReference database;

    MatchAdapter matchAdapter;
    ArrayList<Match> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_page);



        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        database = FirebaseDatabase.getInstance().getReference("matches");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        matchAdapter = new MatchAdapter(this, list, new MatchAdapter.ButtonListener() {
            @Override
            public void acceptButton(View v, int position) {
                //Clicked accept button.
                String toUid = null;
                // When accepted, change status to "Aramıyor".
                for(Student holder: MainActivity.global_user_list){
                    if(Objects.equals(holder.getEmail(), list.get(position).getToEmail()) || Objects.equals(holder.getEmail(), list.get(position).getFromEmail())){
                        String curr_uid = holder.getUid();
                        if(Objects.equals(holder.getEmail(), list.get(position).getToEmail())){
                            toUid = holder.getUid();
                        }
                        FirebaseDatabase.getInstance().getReference().child("students").child(curr_uid)
                                .child("status").setValue("Aramıyor");
                    }
                }

                // We have to delete that request right now.
                FirebaseDatabase.getInstance().getReference().child("matches").child(toUid).removeValue();

                // We have to apply a notification.


            }

            @Override
            public void rejectButton(View v, int position) {
                //Clicked reject button.
                String toUid = null;
                // We have to delete that request right now.
                for(Student holder: MainActivity.global_user_list){
                    if(Objects.equals(holder.getEmail(), list.get(position).getToEmail())){
                        toUid = holder.getUid();
                    }

                }

                // We have to delete that request right now.
                FirebaseDatabase.getInstance().getReference().child("matches").child(toUid).removeValue();



                // We have to apply a notification.
            }
        });
        mRecyclerView.setAdapter(matchAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get all data in matches node.
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Match match = dataSnapshot.getValue(Match.class);
                    // Show only that user's incoming match requests.
                    if(Objects.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), match.getToEmail())){
                        list.add(match);
                    }
                }
                matchAdapter.notifyDataSetChanged();
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
                startActivity(new Intent(MatchActivity.this, ProfileActivity.class));
            }
        });

        // Button handler for profile list page.
        MaterialButton profile_list_btn = findViewById(R.id.profile_list_btn);
        profile_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MatchActivity.this, ProfileListActivity.class));
            }
        });

        // Button handler for map page.
        MaterialButton map_btn = findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MatchActivity.this, MapActivity.class));
            }
        });

        // Button handler for match page.
        MaterialButton match_btn = findViewById(R.id.match_btn);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MatchActivity.this, MatchActivity.class));
            }
        });
    }
}
