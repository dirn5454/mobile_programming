package com.example.contactsandsms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button cButton;
    private Button mButton;

    int what = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cButton = findViewById(R.id.cButton);
        mButton = findViewById(R.id.mButton);

        cButton.setOnClickListener(this);
        mButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cButton:
                what = 0;

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("what",what);

                startActivity(intent);

                break;
            case R.id.mButton:
                what = 1;

                Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);
                intent1.putExtra("what",what);

                startActivity(intent1);
                break;
        }



    }
}