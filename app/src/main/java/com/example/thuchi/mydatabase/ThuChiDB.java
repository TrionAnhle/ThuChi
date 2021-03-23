package com.example.thuchi.mydatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.thuchi.model.ThuChi;

public class ThuChiDB{
    SQLiteDatabase database;
    DBHelper dbHelper;

    public ThuChiDB(Context context){
        dbHelper = new DBHelper(context);
        try{
            database = dbHelper.getWritableDatabase();
        }catch (SQLiteException e){
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor layTatCaDuLieu(){
        StringBuilder sql = new StringBuilder("SELECT * FROM "+DBHelper.TB_THUCHI+","+DBHelper.TB_CONGVIEC);
        sql.append(" WHERE "+DBHelper.TB_THUCHI+"._idcongviec = "+DBHelper.TB_CONGVIEC+"._id");
        Cursor cursor = database.rawQuery(sql.toString(),null);
        return cursor;
    }

    public Cursor layDuLieuTheoNgay(String ngay){
        StringBuilder sql = new StringBuilder("SELECT * FROM "+DBHelper.TB_THUCHI+","+DBHelper.TB_CONGVIEC);
        sql.append(" WHERE "+DBHelper.TB_THUCHI+"._idcongviec = "+DBHelper.TB_CONGVIEC+"._id  ");
        sql.append(" AND _ngay = ? ");
        Cursor cursor = database.rawQuery(sql.toString(),new String[]{ngay});
        return cursor;
    }

    public Cursor layNgay(){
        String sql = " SELECT _ngay FROM "+DBHelper.TB_THUCHI;
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public void them(ThuChi thuChi){
        StringBuilder sql = new StringBuilder(" INSERT INTO "+DBHelper.TB_THUCHI);
        sql.append(" ( _idcongviec, _ngay, _sotien) ");
        sql.append(" VALUES("+thuChi.getIdCongViec()+",?,"+thuChi.getSoTien()+" )");
        database.execSQL(sql.toString(), new String[]{thuChi.getNgay()});

    }

    public void xoa(ThuChi thuChi){
        StringBuilder sql = new StringBuilder(" DELETE FROM "+DBHelper.TB_THUCHI);
        sql.append(" WHERE _id = "+thuChi.getId());
        database.execSQL(sql.toString());
    }

    public void sua(ThuChi thuChi){
        StringBuilder sql = new StringBuilder(" UPDATE "+DBHelper.TB_THUCHI+" SET ");
        sql.append(" _idcongviec = "+thuChi.getIdCongViec());
        sql.append(", _ngay = ?");
        sql.append(", _sotien = "+thuChi.getSoTien());
        sql.append(" WHERE _id = "+thuChi.getId());
        database.execSQL(sql.toString(),new String[]{thuChi.getNgay()});
    }
}
