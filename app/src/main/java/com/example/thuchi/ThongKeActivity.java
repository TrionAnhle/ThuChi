package com.example.thuchi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuchi.model.CongViec;
import com.example.thuchi.model.ThuChi;
import com.example.thuchi.mydatabase.CongViecDB;
import com.example.thuchi.mydatabase.ThuChiDB;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {
    PieChart pieChart;
    TextView tvTongChi,tvTienTro,tvTienDien,tvTienNuoc,tvAnUong,tvMuaSam,tvKhac;
    List<CongViec> listCongViec = new ArrayList<>();
    List<ThuChi> listThuChi = new ArrayList<>();
    Long[] listThongKeTungLoai = {0L,0L,0L,0L,0L,0L};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        setControll();
        setEvent();
    }
    public void setControll(){
        pieChart = findViewById(R.id.chartThang);
        tvTongChi = findViewById(R.id.tvTongChi);
        tvTienTro = findViewById(R.id.tvTienTro);
        tvTienDien = findViewById(R.id.tvTienDien);
        tvTienNuoc = findViewById(R.id.tvTienNuoc);
        tvAnUong = findViewById(R.id.tvAnUong);
        tvMuaSam = findViewById(R.id.tvMuaSam);
        tvKhac = findViewById(R.id.tvKhac);
    }

    public void setEvent(){
        ThuChiDB thuChiDB = new ThuChiDB(this);
        CongViecDB congViecDB = new CongViecDB(this);
        loadLoaiCongViec(congViecDB);
        loadThuChi(thuChiDB);
        setUpPieChart(pieChart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuBack){
            Intent intent = new Intent(ThongKeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpPieChart(PieChart pieChart){
        thongKeTungLoai();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.15f);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);

        long sum =0,thCuoi=0;
        for(int i = 0;i<listCongViec.size();i++){
            sum+=(listThongKeTungLoai[listCongViec.get(i).getId()-1]);
        }
        tvTongChi.setText("TỔNG CHI: "+String.valueOf(sum));
        tvTienTro.setText("Tiền Trọ: "+String.valueOf(listThongKeTungLoai[listCongViec.get(0).getId()-1]));
        tvTienDien.setText("Tiền Điện: "+String.valueOf(listThongKeTungLoai[listCongViec.get(1).getId()-1]));
        tvTienNuoc.setText("Tiền Nước: "+String.valueOf(listThongKeTungLoai[listCongViec.get(2).getId()-1]));
        tvAnUong.setText("Ăn Uống: "+String.valueOf(listThongKeTungLoai[listCongViec.get(3).getId()-1]));
        tvMuaSam.setText("Mua Sắm: "+String.valueOf(listThongKeTungLoai[listCongViec.get(4).getId()-1]));
        tvKhac.setText("Khác: "+String.valueOf(listThongKeTungLoai[listCongViec.get(5).getId()-1]));

        ArrayList<PieEntry> yValues = new ArrayList<>();
        for(int i = 0;i<listCongViec.size();i++){
            int phantram = (int) (((float)listThongKeTungLoai[listCongViec.get(i).getId()-1]/sum)*100);
            if(phantram>0){
                yValues.add(new PieEntry(phantram,listCongViec.get(i).getTenCongViec()));
            }

        }

        PieDataSet dataSet = new PieDataSet(yValues,"Loại Công Việc");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
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
        }
    }

    public void thongKeTungLoai(){
        Calendar c = Calendar.getInstance();
        int nam = c.get(Calendar.YEAR);
        int thang = c.get(Calendar.MONTH)+1;
        String thangNam=String.valueOf(thang)+"/"+String.valueOf(nam);
        for(int i = 0;i<listThuChi.size();i++){
            if(listThuChi.get(i).getNgay().indexOf(thangNam)!=-1){
                int id = listThuChi.get(i).getIdCongViec();
                listThongKeTungLoai[id-1]+=listThuChi.get(i).getSoTien();
            }
        }
        for(int i=0;i<listCongViec.size();i++){
            int id = listCongViec.get(i).getId();
        }
    }
}
