package com.example.mymemorygame_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.*;

import java.sql.Time;


//class ContinueCountDownTimer extends TimeMode {
//
//}
public class PauseActivity extends AppCompatActivity {


    RelativeLayout layout_continue, layout_sound,
            layout_choose_mode, layout_play_again, layout_go_home, layout_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_acctivity);

        layout_continue = findViewById(R.id.layout_continue);
//        layout_sound = findViewById(R.id.layout_sound);
        layout_choose_mode = findViewById(R.id.layout_choose_mode);
        layout_play_again = findViewById(R.id.layout_play_again);
        layout_go_home = findViewById(R.id.layout_go_home);
        layout_history = findViewById(R.id.layout_history);
        eventClick();


    }
    public void eventClick() {

        layout_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        layout_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, DiemCaoNhat.class);
                startActivity(intent);
                finish();
            }
        });


        layout_choose_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, ChooseMode.class);
                startActivity(intent);
                finish();
            }
        });

        layout_play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, TimeMode.class);
                startActivity(intent);
                finish();
            }
        });

        layout_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PauseActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
