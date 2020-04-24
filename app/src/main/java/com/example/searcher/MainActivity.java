package com.example.searcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_COUNTER = "counter";

    private SharedPreferences mSettings;
    private TextView editText;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> history = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mainAdapter = new MainAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mainAdapter);
        layoutManager = new LinearLayoutManager(this);
        editText = findViewById(R.id.edit_search);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter);
        history = new ArrayList<>();
        loadText();
        addTextListener();
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.add(editText.getText().toString());
                mainAdapter.update(history);
                mainAdapter.notifyDataSetChanged();
            }
        });


    }

    public void addTextListener(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i <history.size() ; i++) {
                    String text = history.get(i).toLowerCase();
                    if (text.contains(s)){
                        list.add(history.get(i));
                    }
                }
                mainAdapter.update(list);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void loadText() {
        SharedPreferences prefs = getSharedPreferences("share", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        Set<String> strings = new HashSet<>();
        strings = prefs.getStringSet("array", strings);
        history.addAll(strings);
        mainAdapter.update(history);
        editText.setText(name);
    }

    public void saveText() {
        SharedPreferences.Editor editor = getSharedPreferences("share", MODE_PRIVATE).edit();
        editor.putString("name", editText.getText().toString());
        Set<String> set = new HashSet<String>();
        set.addAll(history);
        editor.putStringSet("array", set);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}

