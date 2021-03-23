package com.example.thuchi.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TEN_DATABASE = "QuanLyThuChi";
    public static final String TB_THUCHI = "ThuChi";
    public static final String TB_CONGVIEC = "CongViec";

    public static final String TAO_BANG_THUCHI = ""
            +" create table "+ TB_THUCHI+" ( "
            +" _id integer primary key autoincrement , "
            +" _idcongviec integer not null, "
            +" _ngay text not null,"
            +" _sotien integer not null);";

    public static final String TAO_BANG_CONGVIEC=""
            +" create table "+ TB_CONGVIEC+" ( "
            +" _id integer primary key autoincrement , "
            +" _dau text not null, "
            +" _tencongviec text not null);";

    public DBHelper(@Nullable Context context) {
        super(context, TEN_DATABASE, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_CONGVIEC);
        db.execSQL(TAO_BANG_THUCHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
