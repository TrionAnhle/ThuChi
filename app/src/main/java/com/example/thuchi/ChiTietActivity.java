package com.example.thuchi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuchi.model.CongViec;
import com.example.thuchi.model.ThuChi;
import com.example.thuchi.model.ThuChiAdapter;
import com.example.thuchi.mydatabase.CongViecDB;
import com.example.thuchi.mydatabase.ThuChiDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChiTietActivity extends AppCompatActivity {
    private EditText edtCTNgay;
    private Button btnCTChonNgay;
    private ListView lvCTTatCa;
    ThuChiAdapter thuChiAdapter;
    private List<ThuChi> listThuChi= new ArrayList<>();
    private List<CongViec> listCongViec = new ArrayList<>();
    private List<String> listTenCongViec = new ArrayList<>();
    String ngayChon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        setControll();
        setEvent(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuBack){
            Intent intent = new Intent(ChiTietActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setControll(){
        edtCTNgay = findViewById(R.id.edtCTNgay);
        btnCTChonNgay = findViewById(R.id.btnCTChonNgay);
        lvCTTatCa = findViewById(R.id.lvCTTatCa);
    }
    public void setEvent(final Context contextParent){
        final ThuChiDB thuChiDB = new ThuChiDB(this);
        CongViecDB congViecDB = new CongViecDB(this);
        thuChiAdapter = new ThuChiAdapter(listThuChi);
        lvCTTatCa.setAdapter(thuChiAdapter);
        loadThuChi(thuChiDB);
        loadLoaiCongViec(congViecDB);

        lvCTTatCa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogChiTiet(position,thuChiDB);
            }
        });

        btnCTChonNgay.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int nam = c.get(Calendar.YEAR);
            int thang = c.get(Calendar.MONTH);
            int ngay = c.get(Calendar.DAY_OF_MONTH);



            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(contextParent,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String ngay = String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)
                                        +"/"+String.valueOf(year);
                                edtCTNgay.setText(ngay);
                                ngayChon = ngay;
                                loadThuChiTheoNgay(thuChiDB,ngay);
                            }
                        },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
    }
    public void loadThuChi(ThuChiDB thuChiDB){
        Cursor cursor = thuChiDB.layTatCaDuLieu();
        if(cursor!=null){
            listThuChi.clear();
            while (cursor.moveToNext()){
                ThuChi thuChi= new ThuChi();
                thuChi.setId(cursor.getInt(0));
                thuChi.setIdCongViec(cursor.getInt(1));
                thuChi.setNgay(cursor.getString(2));
                thuChi.setSoTien(cursor.getInt(3));
                thuChi.setDau(cursor.getString(5));
                thuChi.setTenCongViec(cursor.getString(6));
                listThuChi.add(thuChi);
            }
            thuChiAdapter.notifyDataSetChanged();
        }
    }
    public void loadThuChiTheoNgay(ThuChiDB thuChiDB, String ngay){
        Cursor cursor = thuChiDB.layDuLieuTheoNgay(ngay);
        if(cursor!=null){
            listThuChi.clear();
            while (cursor.moveToNext()){
                ThuChi thuChi= new ThuChi();
                thuChi.setId(cursor.getInt(0));
                thuChi.setIdCongViec(cursor.getInt(1));
                thuChi.setNgay(cursor.getString(2));
                thuChi.setSoTien(cursor.getInt(3));
                thuChi.setDau(cursor.getString(5));
                thuChi.setTenCongViec(cursor.getString(6));
                listThuChi.add(thuChi);
            }
            thuChiAdapter.notifyDataSetChanged();
        }
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
        spnDlogTenCongViec.setSelection(listTenCongViec.indexOf(listThuChi.get(vitri).getTenCongViec()));

        tvDlogNgay.setText(listThuChi.get(vitri).getNgay());
        edtDlogSoTien.setText(listThuChi.get(vitri).getSoTien().toString());
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
                thuChiDB.xoa(listThuChi.get(vitri));
                if(ngayChon!=null){
                    loadThuChiTheoNgay(thuChiDB,ngayChon);
                }else loadThuChi(thuChiDB);
                dialog.dismiss();
            }
        });
        btnDlogSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuChi thuChi = new ThuChi();
                thuChi.setId(listThuChi.get(vitri).getId());
                // xu ly idcongviec
                String tenCongViec = spnDlogTenCongViec.getSelectedItem().toString();
                for(CongViec item : listCongViec){
                    if(item.getTenCongViec().equalsIgnoreCase(tenCongViec)){
                        thuChi.setIdCongViec(item.getId());
                    }
                }
                // xu ly ngay
                thuChi.setNgay(listThuChi.get(vitri).getNgay());
                // xu ly so tien
                if(edtDlogSoTien.getText().toString().length()>10){
                    edtDlogSoTien.setError("Bạn đã nhập quá số tiền cho phép");
                    edtDlogSoTien.requestFocus();return;
                }
                thuChi.setSoTien(Integer.parseInt( edtDlogSoTien.getText().toString()));
                thuChiDB.sua(thuChi);
                if(ngayChon!=null){
                    loadThuChiTheoNgay(thuChiDB,ngayChon);
                }else loadThuChi(thuChiDB);

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
