package com.example.pradhyumna.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesAdding extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText editTextTitle,editText;
    int noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_adding);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.pradhyumna.notes" , MODE_PRIVATE);
        editText = findViewById(R.id.editText);
        editTextTitle=findViewById(R.id.editText2);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("notesId" , -1);

        if(noteid != -1){
            editText.setText(MainActivity.notes.get(noteid));
            editTextTitle.setText(MainActivity.titles.get(noteid));
        }
        else{
            MainActivity.notes.add("");
            MainActivity.titles.add("");
            noteid = MainActivity.notes.size() -1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteid , String.valueOf(s));



                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes" , set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.titles.set(noteid,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                HashSet<String> titleSet= new HashSet<>(MainActivity.titles);
                sharedPreferences.edit().putStringSet("titles" , titleSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
