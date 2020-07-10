package com.example.todolist;


import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class MainActivity extends AppCompatActivity {


    ArrayList<HashMap<String, String>> toDoList;
    ListView listView;
    TextView addName, addPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    private void loadActivity() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listview);
        addName = findViewById(R.id.addName);
        addPriority = findViewById(R.id.addPriority);
        toDoList = new ArrayList<>();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInsertAlertDialog();
            }
        });
    }



    protected void showInsertAlertDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);


        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.addlist, null);
        ad.setView(view);
        ad.setTitle("Add new ToDo");

        final AlertDialog dialog = ad.create();

        dialog.show();
    }

}
