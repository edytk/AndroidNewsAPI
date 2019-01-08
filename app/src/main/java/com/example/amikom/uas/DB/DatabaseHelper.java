package com.example.amikom.uas.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Master on 05-Jan-19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static  final String DATABASE_NAME = "news.db";
    public  static  final String TABLE_NAME = "user";
    public  static  final String COL_1 = "id_user";
    public  static  final String COL_2 = "username";
    public  static  final String COL_3 = "password";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user(id_user INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS user ");
        onCreate(db);
    }

    public long addUser (String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("name" , name);
        contentValues.put("username" , user);
        contentValues.put("password" , password);
        long res = db.insertOrThrow("user" , null, contentValues);
        db.close();
        return res;

    }

    public boolean checkUser(String username, String password) {
        String[] columns = { COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?" ;
        String[] selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_NAME, columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateUser(String id_user, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id_user);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        db.update(TABLE_NAME, contentValues, "id_user = ?",new String[] { id_user });
        return true;
    }

    public Integer deleteData (String id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id_user = ?",new String[] {id_user});
    }

}
