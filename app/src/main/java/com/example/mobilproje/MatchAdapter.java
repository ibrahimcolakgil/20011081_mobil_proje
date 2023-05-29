package com.example.mobilproje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    Context context;
    ArrayList<Match> list;

    StudentAdapter.buttonClickListener buttonClickListener;
    public static ButtonListener onClickListener;

    public MatchAdapter(Context context, ArrayList<Match> list, ButtonListener listener) {
        this.context = context;
        this.list = list;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.match_item, parent, false);
        return new MatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = list.get(position);
        holder.email.setText(match.getFromEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder{
        TextView email, phone_number;
        MaterialButton accept_btn, reject_btn;
        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.students_item_email_info);
            phone_number = itemView.findViewById(R.id.students_item_phone_number_info);

            accept_btn = itemView.findViewById(R.id.accept_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);

            accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.acceptButton(view, getAdapterPosition());
                }
            });

            reject_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.rejectButton(view, getAdapterPosition());
                }
            });
        }
    }

    public interface ButtonListener{
        void acceptButton(View v, int position);
        void rejectButton(View v, int position);
    }
}
