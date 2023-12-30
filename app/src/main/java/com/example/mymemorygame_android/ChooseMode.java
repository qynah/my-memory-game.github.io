package com.example.mymemorygame_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChooseMode extends AppCompatActivity {

    FloatingActionButton fab_return;
    Button btnlevel1, btnlevel2, btnlevel3, btnlevel4, btnlevel5, btnlevel6, btnlevel7, btnlevel8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);

        fab_return = findViewById(R.id.fab_return);
        btnlevel1 = findViewById(R.id.btnlevel1);
        btnlevel2 = findViewById(R.id.btnlevel2);
        btnlevel3 = findViewById(R.id.btnlevel3);
        btnlevel4 = findViewById(R.id.btnlevel4);
        btnlevel5 = findViewById(R.id.btnlevel5);
        btnlevel6 = findViewById(R.id.btnlevel6);
        btnlevel7 = findViewById(R.id.btnlevel7);
        btnlevel8 = findViewById(R.id.btnlevel8);

        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ChooseMode.this, HomePage.class);
                startActivity(intent);
            }
        });
        btnlevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ChooseMode.this, TimeMode.class);
                startActivity(intent);
            }
        });
        btnlevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ChooseMode.this, TimeModeLV2.class);
                startActivity(intent);
            }
        });
        btnlevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 3!", Toast.LENGTH_LONG).show();

            }
        });
        btnlevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 4!", Toast.LENGTH_LONG).show();

            }
        });
        btnlevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 5!", Toast.LENGTH_LONG).show();

            }
        });
        btnlevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 6!", Toast.LENGTH_LONG).show();

            }
        });
        btnlevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 7!", Toast.LENGTH_LONG).show();

            }
        });
        btnlevel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMode.this, "Chuyển sang LEVEL 8!", Toast.LENGTH_LONG).show();

            }
        });
    }
}