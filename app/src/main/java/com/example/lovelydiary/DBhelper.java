package com.example.lovelydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE_DIARY=
            "create table diary (" +
            "id integer primary key autoincrement, "
            +"title text,"
            +"author text,"
            +"date text,"
            +"content text,"
            +"image text)";

    private Context mContext;

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DIARY);
        Toast.makeText(mContext,"create succeeded!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists diary");
        db.execSQL(CREATE_TABLE_DIARY);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
