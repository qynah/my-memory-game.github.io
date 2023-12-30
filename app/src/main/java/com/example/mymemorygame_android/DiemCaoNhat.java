package com.example.mymemorygame_android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DiemCaoNhat extends AppCompatActivity {
    FloatingActionButton fab_return;
    ListView lvDiem;
    List<Item_Diemcaonhat> lsData = new ArrayList<Item_Diemcaonhat>();
    DiemCaoNhatAdapter adapter;
    DiemCaoNhatDB  db = new DiemCaoNhatDB(DiemCaoNhat.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diem_cao_nhat);


        getViews();

        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiemCaoNhat.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        //ánh xạ
        lvDiem = findViewById(R.id.lvDiemCaoNhat);
//        //tao doi tuong DanhBaDB thuc hien initdata
        db.initData();
//        //select du lieu do vao lsData de hien len listview
        lsData = db.getAllDiem();
//        //khoi tao adapter load view va data
        adapter = new DiemCaoNhatAdapter(DiemCaoNhat.this,lsData);
//        //set adapter cho listview
        lvDiem.setAdapter(adapter);



    }
    public void getViews() {
        fab_return = findViewById(R.id.fab_return);
    }

}
