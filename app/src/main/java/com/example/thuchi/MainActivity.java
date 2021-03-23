package com.example.thuchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.thuchi.model.CongViec;
import com.example.thuchi.mydatabase.CongViecDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnThongKe,btnThuChi,btnChiTiet,btnCaiDat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControll();
        setEvent();
    }

    public void setControll(){
        btnThongKe = findViewById(R.id.imgBtnThongKe);
        btnThuChi = findViewById(R.id.imgBtnThuChi);
        btnChiTiet = findViewById(R.id.imgBtnChiTiet);
        btnCaiDat = findViewById(R.id.imgBtnCaiDat);
    }

    public void setEvent(){
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });
        btnThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThuChiActivity.class);
                startActivity(intent);
            }
        });
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChiTietActivity.class);
                startActivity(intent);
            }
        });
        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaiDatActivity.class);
                startActivity(intent);
            }
        });
    }
}
