package com.example.pradhyumna.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView notesList;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<String> titles = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.pradhyumna.notes" , MODE_PRIVATE);
        notesList = findViewById(R.id.notesListView);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes" , null);
        HashSet<String> titleSet = (HashSet<String>) sharedPreferences.getStringSet("titles" , null);

        if(set == null){
            notes.add("Empty");
            titles.add("Example note");
        }
        else{
            notes = new ArrayList(set);
            titles=new ArrayList<>(titleSet);
        }

        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , titles);
        notesList.setAdapter(arrayAdapter);


        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , NotesAdding.class);
                intent.putExtra("notesId" , position);
                startActivity(intent);
            }
        });
        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure !?")
                        .setMessage("Are you sure to delete the note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                titles.remove(position);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes" , set).apply();
                                HashSet<String> titleSet= new HashSet<>(MainActivity.titles);
                                sharedPreferences.edit().putStringSet("titles" , titleSet).apply();
                            }
                        })
                         .setNegativeButton("No" , null).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note , menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.addnote){

            Intent intent = new Intent(MainActivity.this , NotesAdding.class);
            startActivity(intent);
            return  true;
        }
            return false;
    }

}
