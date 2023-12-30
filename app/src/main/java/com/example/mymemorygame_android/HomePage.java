package com.example.mymemorygame_android;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class HomePage extends AppCompatActivity {
//    com.google.android.material.button.MaterialButton btnPlay;
    ImageButton btnPlay, btnBangXepHang;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        getView();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setBackgroundColor(getResources().getColor(R.color.white));
//                btnPlay.setStrokeColor(ContextCompat.getColor(this, R.color.black));

                Intent intent = new Intent(HomePage.this, ChooseMode.class);
                startActivity(intent);

            }
        });
        btnBangXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, DiemCaoNhat.class);
                startActivity(intent);
            }
        });
    }
    public void getView() {
        btnPlay = findViewById(R.id.btnPlay);
        btnBangXepHang = findViewById(R.id.btnBangXepHang);


    }
}