package com.example.thuchi;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuchi.model.CongViec;
import com.example.thuchi.model.ThuChi;
import com.example.thuchi.model.ThuChiAdapter;
import com.example.thuchi.mydatabase.CongViecDB;
import com.example.thuchi.mydatabase.ThuChiDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThuChiActivity extends AppCompatActivity {
    private Spinner cbxLoaiCongViec;
    private EditText editTextSoTien;
    private Button btnThem;
    private ListView lvThuChiNgay;
    List<CongViec> listCongViec = new ArrayList<>();
    List<ThuChi> listThuChiNgay = new ArrayList<>();
    List<String> listTenCongViec = new ArrayList<>();
    ThuChiAdapter thuChiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuchi);
        setControll();
        setEvent();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuBack){
            Intent intent = new Intent(ThuChiActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setControll(){
        cbxLoaiCongViec = findViewById(R.id.spnDlogTenCongViec);
        editTextSoTien = findViewById(R.id.edtDlogSoTien);
        btnThem = findViewById(R.id.buttonThem);
        lvThuChiNgay = findViewById(R.id.lvThuChiNgay);
    }

    public void setEvent(){
        CongViecDB congViecDB = new CongViecDB(this);
        final ThuChiDB thuChiDB = new ThuChiDB(this);
        loadLoaiCongViec(congViecDB);
        ArrayAdapter loaiCongViecAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listTenCongViec);
        cbxLoaiCongViec.setAdapter(loaiCongViecAdapter);
        thuChiAdapter = new ThuChiAdapter(listThuChiNgay);
        loadThuChiNgay(thuChiDB);

        lvThuChiNgay.setAdapter(thuChiAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuChi thuChi = new ThuChi();
                // xu ly idcongviec
                String tenCongViec = cbxLoaiCongViec.getSelectedItem().toString();
                for(CongViec item : listCongViec){
                    if(item.getTenCongViec().equalsIgnoreCase(tenCongViec)){
                        thuChi.setIdCongViec(item.getId());
                    }
                }
                // xu ly ngay
                thuChi.setNgay(layNgayHienTai());
                // xu ly so tien
                if(editTextSoTien.getText().toString().length()>10){
                    editTextSoTien.setError("Bạn đã nhập quá số tiền cho phép");
                    editTextSoTien.requestFocus();return;
                }
                if(editTextSoTien.getText().toString().equals("")){
                    editTextSoTien.setError("Bạn chưa nhập số tiền");
                    editTextSoTien.requestFocus();return;
                }
                if(editTextSoTien.getText().toString().length()>10){
                    editTextSoTien.setError("Số tiền quá mức quy định");
                    editTextSoTien.requestFocus();return;
                }
                thuChi.setSoTien(Integer.parseInt(editTextSoTien.getText().toString()));
                thuChiDB.them(thuChi);
                loadThuChiNgay(thuChiDB);
            }
        });

        lvThuChiNgay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogChiTiet(position,thuChiDB);
            }
        });
    }

    public void loadLoaiCongViec(CongViecDB congViecDB){
        Cursor cursor = congViecDB.layTatCaDuLieu();
        if(cursor!=null){
            listCongViec.clear();
            while(cursor.moveToNext()){
                CongViec congViec = new CongViec();
                congViec.setId(cursor.getInt(0));
                congViec.setDau(cursor.getString(1));
                congViec.setTenCongViec(cursor.getString(2));
                listCongViec.add(congViec);
            }
        }
        for(int i=0;i<listCongViec.size();i++){
            listTenCongViec.add(listCongViec.get(i).getTenCongViec());
        }
    }
    public void loadThuChiNgay(ThuChiDB thuChiDB){
        Cursor cursor = thuChiDB.layDuLieuTheoNgay(layNgayHienTai());
        if(cursor!=null){
            listThuChiNgay.clear();
            while (cursor.moveToNext()){
                ThuChi thuChi= new ThuChi();
                thuChi.setId(cursor.getInt(0));
                thuChi.setIdCongViec(cursor.getInt(1));
                thuChi.setNgay(cursor.getString(2));
                thuChi.setSoTien(cursor.getInt(3));
                thuChi.setDau(cursor.getString(5));
                thuChi.setTenCongViec(cursor.getString(6));
                listThuChiNgay.add(thuChi);
            }
           thuChiAdapter.notifyDataSetChanged();
        }
    }
    public String layNgayHienTai(){
        Calendar c = Calendar.getInstance();
        String ngay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String thang = String.valueOf(c.get(Calendar.MONTH)+1);
        String nam = String.valueOf(c.get(Calendar.YEAR));
        return (ngay+"/"+thang+"/"+nam);
    }

    private void DialogChiTiet(final int vitri, final ThuChiDB thuChiDB){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_xoa_thuchi);

        TextView tvDlogNgay = dialog.findViewById(R.id.tvDlogNgay);
        final Spinner spnDlogTenCongViec = dialog.findViewById(R.id.spnDlogTenCongViec);
        final EditText edtDlogSoTien = dialog.findViewById(R.id.edtDlogSoTien);
        Button btnDlogXoa = dialog.findViewById(R.id.btnDlogXoa);
        Button btnDlogSua = dialog.findViewById(R.id.btnDlogSua);
        Button btnDlogHuy = dialog.findViewById(R.id.btnDlogHuy);
        // lay du lieu
        ArrayAdapter dlogloaiCongViecAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listTenCongViec);
        spnDlogTenCongViec.setAdapter(dlogloaiCongViecAdapter);
        spnDlogTenCongViec.setSelection(listTenCongViec.indexOf(listThuChiNgay.get(vitri).getTenCongViec()));

        tvDlogNgay.setText(listThuChiNgay.get(vitri).getNgay());
        edtDlogSoTien.setText(listThuChiNgay.get(vitri).getSoTien().toString());
        // set su kien
        btnDlogHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDlogXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuChiDB.xoa(listThuChiNgay.get(vitri));
                loadThuChiNgay(thuChiDB);
                dialog.dismiss();
            }
        });
        btnDlogSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuChi thuChi = new ThuChi();
                thuChi.setId(listThuChiNgay.get(vitri).getId());
                // xu ly idcongviec
                String tenCongViec = spnDlogTenCongViec.getSelectedItem().toString();
                for(CongViec item : listCongViec){
                    if(item.getTenCongViec().equalsIgnoreCase(tenCongViec)){
                        thuChi.setIdCongViec(item.getId());
                    }
                }
                // xu ly ngay
                thuChi.setNgay(layNgayHienTai());
                // xu ly so tien
                if(edtDlogSoTien.getText().toString().length()>10){
                    edtDlogSoTien.setError("Bạn đã nhập quá số tiền cho phép");
                    edtDlogSoTien.requestFocus();return;
                }
                thuChi.setSoTien(Integer.parseInt( edtDlogSoTien.getText().toString()));
                thuChiDB.sua(thuChi);
                loadThuChiNgay(thuChiDB);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
