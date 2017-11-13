package com.example.mini_cexentrustment.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mini_cexentrustment.define.GDefine;

/**
 * Created by 信威 on 2017/10/23.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    private  static SQLiteDatabase database; //固定的欄位變數

    //建構子，在一般的應用都不需要修改
    public  MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context, name, factory, version);
    }

    //需要資料庫的元件呼叫這個方法，這個方法，在一般的應用都不需要修改
    public  static  SQLiteDatabase getDatabase(Context context){
        if(database == null || !database.isOpen()) {
            database = new MyDBHelper(context, GDefine.DATABASE_NAME, null, GDefine.DATABASE_VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyDBHelper", "建立應用程式需要的表格");
        //建立應用程式需要的表格
        db.execSQL(UserAccountDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //刪除原有的表格
        Log.d("MyDBHelper", "刪除原有的表格");
        db.execSQL("DROP TABLE IF EXISTS " + GDefine.TABLE_NAME_USERACCOUNT);
        //呼叫 onCreate 建立新的表格
        onCreate(db);
    }
}
