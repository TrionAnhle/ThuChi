package com.example.thuchi.mydatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.thuchi.model.CongViec;

public class CongViecDB {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public CongViecDB(Context context){
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
        String sql = " SELECT * FROM "+DBHelper.TB_CONGVIEC;
        Cursor cursor = database.rawQuery(sql, null);
        return  cursor;
    }

    public void them(CongViec congViec){
        StringBuilder sql = new StringBuilder(" INSERT INTO "+DBHelper.TB_CONGVIEC);
        sql.append(" ( _dau, _tencongviec) ");
        sql.append(" VALUES(?,?) ");
        database.execSQL(sql.toString(),new String[]{
                congViec.getDau(),congViec.getTenCongViec()
        });
    }

    public void xoa(CongViec congViec){
        StringBuilder sql = new StringBuilder(" DELETE FROM "+DBHelper.TB_CONGVIEC);
        sql.append(" WHERE _id = "+congViec.getId());
        database.execSQL(sql.toString());
    }

    public void sua(CongViec congViec){
        StringBuilder sql = new StringBuilder(" UPDATE "+DBHelper.TB_CONGVIEC+" SET ");
        sql.append(" _dau = ?");
        sql.append(", _tencongviec = ?");
        sql.append(" WHERE _id = "+congViec.getId());
        database.execSQL(sql.toString(),new String[]{congViec.getDau(),congViec.getTenCongViec()});
    }
}
