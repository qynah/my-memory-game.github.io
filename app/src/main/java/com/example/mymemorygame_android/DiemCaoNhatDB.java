package com.example.mymemorygame_android;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiemCaoNhatDB extends SQLiteOpenHelper {
    public static final String DB_NAME="diemcaonhat3.db";
    private static final int DB_VERSION=1;
    //dinh nghia ten bang, ten cac cot
    private static final String TB_NAME="tbl_diemcaonhat";
    private static final String XEPHANG="xepHang";
    private static final String TEN="tenNguoiChoi";
    private static final String DIEM="diemSo";
    Context context;

    public DiemCaoNhatDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        String query ="CREATE TABLE IF NOT EXISTS " + TB_NAME + "( "
                + XEPHANG+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEN +" TEXT, "
                + DIEM +" INTEGER )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    public void initData(){
        //kiem tra xem database da co du lieu chua
        int count = getDbCount();
        if(count==0){
            //tao du lieu mau
            Item_Diemcaonhat db1 = new Item_Diemcaonhat(0,"Tho",1000);
            Item_Diemcaonhat db2 = new Item_Diemcaonhat(1,"Gau", 900);
            Item_Diemcaonhat db3 = new Item_Diemcaonhat(2,"Nai",800);
            Item_Diemcaonhat db4 = new Item_Diemcaonhat(3,"Ho", 700);
            Item_Diemcaonhat db5 = new Item_Diemcaonhat(4,"Cao",600);
            Item_Diemcaonhat db6 = new Item_Diemcaonhat(5,"Meo", 500);
            Item_Diemcaonhat db7 = new Item_Diemcaonhat(6,"Chuot",400);
            Item_Diemcaonhat db8 = new Item_Diemcaonhat(7,"Rua", 300);
            Item_Diemcaonhat db9 = new Item_Diemcaonhat(8,"Khi",200);
            Item_Diemcaonhat db10 = new Item_Diemcaonhat(9,"Huou", 100);

            //insert du lieu vao database
            if(insertDiem(db1)==-1||insertDiem(db2)==-1||
                    insertDiem(db3)==-1||insertDiem(db4)==-1||
                    insertDiem(db5)==-1||insertDiem(db6)==-1||
                    insertDiem(db7)==-1||insertDiem(db8)==-1||
                    insertDiem(db9)==-1||insertDiem(db10)==-1) {
                Toast.makeText(this.context, "Insert failed! ",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public long insertDiem(Item_Diemcaonhat diemcaonhat){
        //tao doi tuong ContentValues de chua gia tri can insert
        ContentValues values = new ContentValues();
        values.put(XEPHANG, diemcaonhat.getXephang());
        values.put(TEN, diemcaonhat.getTennguoichoi());
        values.put(DIEM, diemcaonhat.getDiemso());
        //thuc hien insert du lieu
        return this.getWritableDatabase().
                insert(TB_NAME,null,values);
    }

    public int updateDiem(Item_Diemcaonhat diemcaonhat, int xephang){
        ContentValues values = new ContentValues();
        values.put(XEPHANG, diemcaonhat.getXephang());
        values.put(TEN, diemcaonhat.getTennguoichoi());
        values.put(DIEM, diemcaonhat.getDiemso());
        String whereArg[] ={xephang+""};
        return this.getWritableDatabase().update(TB_NAME,values,
                XEPHANG+"=?", whereArg);
    }

    public int deleteDiem(int xephang){
        String whereArg[]={xephang+""};
        return this.getWritableDatabase().delete(TB_NAME,
                XEPHANG+"=?", whereArg);
    }

    public int deleteDiem(String ten){
        String whereArg[]={ten};
        return this.getWritableDatabase().delete(TB_NAME,
                TEN+"=?", whereArg);
    }
    public int getDbCount(){
        Cursor cursor = this.getReadableDatabase().
                rawQuery("SELECT * FROM "+ TB_NAME,null);
        return cursor.getCount();
    }

    public List<Item_Diemcaonhat> getAllDiem(){
        List<Item_Diemcaonhat> kq = new ArrayList<Item_Diemcaonhat>();
        Cursor cursor = this.getReadableDatabase().
                rawQuery("SELECT * FROM " + TB_NAME, null);
        //duyet qua danh sach ban ghi co trong con tro
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                //thuc hien insert du lieu vao list kq
                do{
                    Item_Diemcaonhat db = new Item_Diemcaonhat();
                    db.setXephang(cursor.getInt(0));
                    db.setTennguoichoi(cursor.getString(1));
                    db.setDiemso(cursor.getInt(2));
                    kq.add(db);
                }while(cursor.moveToNext());

            }
        }
        cursor.close();//dong con tro sau khi ket thuc truy van
        return kq;
    }

    public void updateDiem(Item_Diemcaonhat item_diemcaonhat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEN, item_diemcaonhat.tennguoichoi);
        values.put(DIEM, item_diemcaonhat.diemso);

        db.update(TB_NAME, values, XEPHANG + " = "+ item_diemcaonhat.xephang, null);

    }
}
