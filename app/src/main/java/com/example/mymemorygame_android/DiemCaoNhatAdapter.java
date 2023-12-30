package com.example.mymemorygame_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DiemCaoNhatAdapter extends ArrayAdapter<Item_Diemcaonhat> {
    Context context;
    List<Item_Diemcaonhat> listData;
    DiemCaoNhatAdapter diemCaoNhatAdapter;

    public DiemCaoNhatAdapter(@NonNull Context context, List<Item_Diemcaonhat> listDiem) {
        super(context, 0, listDiem);
        this.context = context;
        listData = listDiem;
        diemCaoNhatAdapter = this;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Nullable
    @Override
    public Item_Diemcaonhat getItem(int position) {

        return listData.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =convertView;
        if(view ==null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_diem,
                    parent,false);
        }
        Item_Diemcaonhat diemcaonhat = listData.get(position);

        TextView tvSTT = view.findViewById(R.id.tvSTT);
        TextView tvTen = view.findViewById(R.id.tvTen);
        TextView tvDiem = view.findViewById(R.id.tvDiem);

        //data binding

        tvSTT.setText((diemcaonhat.getXephang()+1) +"");
        tvTen.setText(diemcaonhat.getTennguoichoi());
        tvDiem.setText(diemcaonhat.getDiemso()+"");

        return view;
    }
//    ActivityResultLauncher<Intent> callUpd = ((AppCompatActivity) getContext()).registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    //update diem cao nhat
//                    if(result.getResultCode()== Activity.RESULT_OK) {
//                        //lay ra doi tuong intent chua kq tra ve
//                        Intent data = result.getData();
//                        String action = data.getStringExtra("action");
//                        if(action.equals("update")) {
//                            //update du lieu
//                            Item_Diemcaonhat db =(Item_Diemcaonhat) data.getExtras().
//                                    getSerializable("updDiemCaoNhat");
//                            DiemCaoNhatDB database = new DiemCaoNhatDB(context);
//                            if(database.updDanhBa(db, db.getId())>0) {
//                                Toast.makeText(context, "update success!",
//                                        Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(context, "update failed",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                            //refresh adapter
//                            listData = database.getAllDiem();
//                            diemCaoNhatAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            });
}
