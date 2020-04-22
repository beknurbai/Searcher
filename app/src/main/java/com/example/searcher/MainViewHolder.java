package com.example.searcher;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MainViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        textView=itemView.findViewById(R.id.vh_text_view);


    }

    public void bind(String s){
        textView.setText(s);
    }
}
