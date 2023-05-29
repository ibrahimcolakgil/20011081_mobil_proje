package com.example.mobilproje;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private EditText email, password, department, grade, status, distance, shared, phone_num;

    private String email_s, password_s, department_s, grade_s, status_s, distance_s, shared_s, phone_num_s;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ImageView imageView;
    private MaterialButton save_button;

    private Uri imgUri;
    private Bitmap imgPicked;

    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference profilePicRef;

    private static final int REQUEST_IMAGE_PICK = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        email = (EditText)findViewById(R.id.email_et);
        password = (EditText)findViewById(R.id.password_et);
        department = (EditText)findViewById(R.id.department_et);
        grade = (EditText)findViewById(R.id.grade_et);
        status = (EditText)findViewById(R.id.status_et);
        distance = (EditText)findViewById(R.id.distance_et);
        shared = (EditText)findViewById(R.id.shared_time_et);
        phone_num = (EditText)findViewById(R.id.phone_num_et);

        imageView = (ImageView)findViewById(R.id.image_iv);

        save_button = (MaterialButton)findViewById(R.id.save_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("students/").child(firebaseUser.getUid());

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        StorageReference profilePicRef = storageRef.child("images/").child(databaseReference.child("uid").toString());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Student user = snapshot.getValue(Student.class);

                email.setText(user.getEmail());
                password.setText(user.getPassword());
                department.setText(user.getDepartment());
                grade.setText(user.getGrade());
                status.setText(user.getStatus());
                distance.setText(user.getDistance_from_campus());
                shared.setText(user.getShare_time());
                phone_num.setText(user.getPhone_number());

                profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Load the profile picture into the ImageView using a library like Glide or Picasso
                        Glide.with(ProfileActivity.this)
                                .load(uri)
                                .centerCrop()
                                .into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred while retrieving the download URL
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Triggers when clicked 'Save' button on profile fragment.
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We are checking if all the fields are filled or not. All fields are mandatory to fill.
                // Edit user's data in firebase.

                email_s = email.getText().toString();
                password_s = password.getText().toString();
                department_s = department.getText().toString();
                grade_s = grade.getText().toString();
                status_s = status.getText().toString();
                distance_s = distance.getText().toString();
                shared_s = shared.getText().toString();
                phone_num_s = phone_num.getText().toString();

                databaseReference.child("email").setValue(email_s);
                databaseReference.child("password").setValue(password_s);
                databaseReference.child("department").setValue(department_s);
                databaseReference.child("grade").setValue(grade_s);
                databaseReference.child("status").setValue(status_s);
                databaseReference.child("distance_from_campus").setValue(distance_s);
                databaseReference.child("share_time").setValue(shared_s);
                databaseReference.child("phone_number").setValue(phone_num_s);

                //Upload image.

                StorageReference imageRef = storageRef.child("images/" + databaseReference.child("uid"));

                // Get the selected image's URI
                Uri selectedImageUri = imgUri; // Retrieve the selected image URI from your ImageView or any other source

                if(imgUri != null){
                    imageRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.child("images/").child(databaseReference.child("uid").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference.child("profilePicture").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

        // When clicked image view. To upload image.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open gallery.
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        // Button handler for profile page.
        MaterialButton profile_btn = findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            }
        });

        // Button handler for profile list page.
        MaterialButton profile_list_btn = findViewById(R.id.profile_list_btn);
        profile_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ProfileListActivity.class));
            }
        });

        // Button handler for map page.
        MaterialButton map_btn = findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MapActivity.class));
            }
        });

        // Button handler for match page.
        MaterialButton match_btn = findViewById(R.id.match_btn);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MatchActivity.class));
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imgUri = selectedImageUri;
            // Set the selected image to your ImageView
            imageView.setImageURI(selectedImageUri);
        }
    }
}
