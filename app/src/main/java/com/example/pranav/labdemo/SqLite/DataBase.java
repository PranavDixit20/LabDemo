package com.example.pranav.labdemo.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by prana on 16-05-2017.
 */

public class DataBase extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "book_cart";
    SQLiteDatabase sdb;

    public  String Book_NAME="bname";
    String name ;

    public DataBase(Context context) {
        super(context,DATABASE_NAME,null,1);
        sdb=this.getWritableDatabase();
        Log.d("database", "created");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CartTable(String nm){
        this.getWritableDatabase();
        name = nm;
        String bquery = "CREATE TABLE IF NOT EXISTS "+name+"("+Book_NAME+" text )";
        sdb.execSQL(bquery);
        Log.d("table Created",name);
    }

    public String[] cheak(String nm) {
        String un = nm;
        Cursor c;
        int k = 0;
        String x;
        sdb=this.getReadableDatabase();

        c= sdb.rawQuery("select * from "+un+" " ,null);
        String p[]=new String[c.getCount()];

        if(c!=null){
            if(c.moveToFirst()){
                do{
                    x=c.getString(c.getColumnIndex("bname"));
                    p[k]=x;
                    k++;
                }while(c.moveToNext());
            }
        }

        return p;
    }

    public void delete(String mat,String p) {

        sdb = this.getWritableDatabase();
        sdb.execSQL("delete from "+p+" where bname='"+mat+"'");
        Log.d("database qur","done");
        sdb.close();
    }

    public int save(String nam,String un) {

        Cursor c;
        this.getReadableDatabase();

        c = sdb.rawQuery("select * from "+un+" where bname ='" + nam + "'", null);
        if(c.moveToFirst()){

            return 1;

        }
        else {
            sdb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("bname",nam);
            sdb.insert(un,null,cv);

            return 0;
        }
    }
}
