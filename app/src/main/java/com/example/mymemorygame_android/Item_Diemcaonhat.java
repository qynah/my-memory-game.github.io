package com.example.mymemorygame_android;

import java.io.Serializable;

public class Item_Diemcaonhat implements Serializable {
    int xephang;
    String tennguoichoi;
    int diemso;

    public Item_Diemcaonhat(String tennguoichoi, int diemso) {
        this.tennguoichoi = tennguoichoi;
        this.diemso = diemso;
    }
    public Item_Diemcaonhat() {}
    public Item_Diemcaonhat(int xephang, String tennguoichoi, int diemso) {
        this.xephang = xephang;
        this.tennguoichoi = tennguoichoi;
        this.diemso = diemso;
    }

    public int getXephang() {
        return xephang;
    }

    public void setXephang(int xephang) {
        this.xephang = xephang;
    }

    public String getTennguoichoi() {
        return tennguoichoi;
    }

    public void setTennguoichoi(String tennguoichoi) {
        this.tennguoichoi = tennguoichoi;
    }

    public int getDiemso() {
        return diemso;
    }

    public void setDiemso(int diemso) {
        this.diemso = diemso;
    }
}
