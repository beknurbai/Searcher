package com.example.searcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainAdapter adapter;
    private ArrayList<String> list;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
        list = new ArrayList<>();
        search = findViewById(R.id.edit_search);
        textSearch();
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String history = search.getText().toString();
                list.add(0, history);
                adapter.update(list);
            }
        });

        }private void textSearch(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s=s.toString().toLowerCase();
                ArrayList<String > arrayList=new ArrayList<>();
                for (int i = 0; i <list.size() ; i++) {
                    String txt=list.get(i).toLowerCase();
                    if (txt.contains(s)){
                        arrayList.add(list.get(i));
                    }
                    adapter.update(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }

        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences activityPreferences = getSharedPreferences("my_history",MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString("TextValue", list.toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(list);
        SharedPreferences sh=getSharedPreferences("my_history",MODE_PRIVATE);
        String st=sh.getString("TextValue","null");
        String name="";
        for (int i = 0; i <st.length() ; i++) {
            String ch = String.valueOf(st.charAt(i));
            if (ch.equals("[")){
                continue;
            }if (ch.equals("]")){
                list.add(name);
                name="";
                continue;
            }if (ch.equals(",")){
                list.add(name);
                name="";
                continue;
            }
            name+=ch;
        }
    }
}

