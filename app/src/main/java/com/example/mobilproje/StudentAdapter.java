package com.example.mobilproje;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    ArrayList<Student> list;
    buttonClickListener buttonClickListener;

    public StudentAdapter(Context context, ArrayList<Student> list, buttonClickListener buttonClickListener) {
        this.context = context;
        this.list = list;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.students_item, parent, false);
        return new StudentViewHolder(v, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = list.get(position);
        holder.email.setText(student.getEmail());
        holder.phoneNumber.setText(student.getPhone_number());
        holder.time.setText(student.getShare_time());
        holder.distance.setText(student.getDistance_from_campus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView email, phoneNumber, time, distance;
        Button profile_button;

        buttonClickListener buttonClickListener;

        public StudentViewHolder(@NonNull View itemView, buttonClickListener buttonClickListener) {
            super(itemView);

            email = itemView.findViewById(R.id.students_item_email_info);
            phoneNumber = itemView.findViewById(R.id.students_item_phone_number_info);
            time = itemView.findViewById(R.id.students_item_time_info);
            distance = itemView.findViewById(R.id.students_item_distance_info);
            profile_button = itemView.findViewById(R.id.view_profile_btn);

            profile_button.setOnClickListener(this);

            this.buttonClickListener = buttonClickListener;

        }

        @Override
        public void onClick(View view) {
            buttonClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface buttonClickListener{
        void onItemClick(int position);
    }
}
