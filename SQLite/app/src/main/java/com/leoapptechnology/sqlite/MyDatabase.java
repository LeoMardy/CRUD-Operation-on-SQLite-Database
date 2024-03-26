package com.leoapptechnology.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String dbName = "signupform.db";

    public MyDatabase(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE user_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF exists user_table");
        onCreate(sqLiteDatabase);
    }

    public boolean insert_data(String name, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("name", name);
        c.put("username", username);
        c.put("password", password);
        long r = db.insert("user_table", null, c);

        if (r == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean update_data(String name,String username,String password){
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("name",name);
        c.put("password",password);

       Cursor cursor = sd.rawQuery("SELECT* FROM user_table WHERE username=?",new String[]{username});
       if (cursor.getCount()>0){
           long r = sd.update("user_table",c,"username=?",new String[]{username});

           if (r==-1) return false;
           else
               return true;
       }
       return false;
    }

    public boolean delete_data(String username){
        SQLiteDatabase sb = this.getWritableDatabase();
        Cursor cursor = sb.rawQuery("SELECT* FROM user_table WHERE username=?",new String[]{username});

        if (cursor.getCount()>0){
            long r = sb.delete("user_table","username=?",new String[]{username});

            if (r==-1) return false;
            else
                return true;
        }
        return false;
    }
    public Cursor getinfo(){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT* FROM user_table",null);

        return cursor;
    }
}
