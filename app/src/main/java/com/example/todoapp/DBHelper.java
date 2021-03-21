package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,"Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table task(ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists task");
    }

    public boolean insertUserData(String title, String content){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        long result = DB.insert("task",null,contentValues);
        return result != -1;
    }
    public boolean updateUserData(String title, String content, int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        long result = DB.update("task",contentValues,"ID=?",new String[]{String.valueOf(id)});
        return result != -1;
    }
    public boolean deleteUserData(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("task", "ID=?", new String[]{String.valueOf(id)});
        return result != -1;
    }
    public Cursor getAllData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT id, title, content FROM task", null);
    }
    public Cursor getInfo(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT title, content FROM task WHERE ID="+id, null);
    }
}
