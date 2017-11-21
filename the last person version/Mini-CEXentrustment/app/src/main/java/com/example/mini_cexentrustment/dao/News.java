package com.example.mini_cexentrustment.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mini_cexentrustment.define.GDefine;
import com.example.mini_cexentrustment.define.UserAccount;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by user on 2017/11/17.
 */

public class News {
    public static final String KEY_ID = "_id"; //編號，固定不變

    //其他表格欄位
    public static final String news_content  = "news_content";
    public static final String news_newsType  = "news_newsType"; //權限	system:系統管理者    standard:一般    trail:試用
    public static final String news_documentSNo  = "news_documentSNo"; //身份別	manager:管理者    member:成員
    public static final String news_dateTime = "news_dateTime"; //email


    //使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE  " + GDefine.TABLE_NAME_News + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    news_content + " TEXT NOT NULL, " +
                    news_newsType + " TEXT NOT NULL, " +
                    news_documentSNo + " TEXT NOT NULL, " +
                    news_dateTime + " TEXT NOT NULL, ";

    //資料庫物件
    private SQLiteDatabase db;

    //建構子
    public News(Context context){
        db = MyDBHelper.getDatabase(context);
    }

    //關閉資料庫
    public  void  close(){
        db.close();
    }

    //新增參數指定的物件
    public UserAccount insert(UserAccount item){
        //建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        //加入ContentValues物件包裝的新資料
        //第一個參數是欄位名稱，第二個參數是欄位資料
        cv.put(news_content, item.getUserId());
        cv.put(news_newsType, item.getUserAuth());
        cv.put(news_documentSNo, item.getUserType());
        cv.put(news_dateTime, item.getEmail());

        //新增一筆資料並取得編號
        //第一個參數是表格名稱
        //第二個參數是沒有指定欄位值得預設值
        //第三個參數是包裝新增資料的 ContentValues物件
        long id = db.insert(GDefine.TABLE_NAME_USERACCOUNT, null, cv);

        //設定編號
        item.setId(id);

        //回傳結果
        return  item;
    }

    //修改參數指定的物件
    public Boolean update(UserAccount item){
        //建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
/*
        //加入ContentValues物件包裝的新資料
        //第一個參數是欄位名稱，第二個參數是欄位資料
        cv.put(userId, item.getUserId());
        cv.put(userAuth, item.getUserAuth());
        cv.put(userType, item.getUserType());
        cv.put(email, item.getEmail());
        cv.put(userName, item.getUserName());
        cv.put(groupSNo, item.getGroupSNo());
        cv.put(groupName, item.getGroupName());
        cv.put(LoginRole, item.getLoginRole());
        cv.put(mobilePhone, item.getMobilePhone());
        cv.put(phone, item.getPhone());
        cv.put(phoneEx, item.getPhoneEX());
        cv.put(token, item.getToken());
        cv.put(needChangePassword, item.getNeedChangePassword());
        cv.put(create_date, item.getCreateDate());
    */
        //設定修改資料的條件編號
        //格式 [欄位名稱=資料]
        String where = KEY_ID  + "=" + item.getId();

        //執行修改資料必回傳修改資料數量是否成功
        return  db.update(GDefine.TABLE_NAME_USERACCOUNT, cv, where, null) > 0;
    }

    //刪除參數指定編號資料
    public Boolean delete(long id){
        String where = KEY_ID  + "=" + id;
        return  db.delete(GDefine.TABLE_NAME_USERACCOUNT, where, null) > 0;
    }

    //取得所有資料
    public List<UserAccount> getAll(){
        List<UserAccount> result = new ArrayList<>();
        Cursor cursor = db.query(GDefine.TABLE_NAME_USERACCOUNT, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            result.add(getRecord(cursor));
        }
        cursor.close();
        return  result;
    }

    //取得指定編號的資料物件
    public UserAccount get(long id){
        UserAccount mUserAccount = null;
        String where = KEY_ID + "=" + id ;
        Cursor result = db.query(GDefine.TABLE_NAME_USERACCOUNT, null, where, null, null, null, null, null);
        if(result.moveToFirst()){
            mUserAccount = getRecord(result);
        }
        result.close();
        return mUserAccount;
    }


    //Cursor 封裝資料
    public UserAccount getRecord(Cursor cursor){
        UserAccount result = new UserAccount();
        result.setId(cursor.getLong(0));
        result.setUserId(cursor.getString(1));
        result.setUserAuth(cursor.getString(2));
        result.setUserType(cursor.getString(3));
        result.setEmail(cursor.getString(4));
        result.setUserName(cursor.getString(5));
        result.setGroupSNo(cursor.getInt(6));
        result.setGroupName(cursor.getString(7));
        result.setLoginRole(cursor.getString(8));
        result.setMobilePhone(cursor.getString(9));
        result.setPhone(cursor.getString(10));
        result.setPhoneEX(cursor.getString(11));
        result.setToken(cursor.getString(12));
        result.setNeedChangePassword(cursor.getString(13));
        result.setCreateDate(cursor.getString(14));
        return result;
    }

    //取得資料數量
    public  int getCount(){
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + GDefine.TABLE_NAME_USERACCOUNT, null);
        if (cursor.moveToNext()){
            result = cursor.getInt(0);
        }
        return result;
    }

    //取得最新一筆設定控制資料
    public  UserAccount getCurrentObject(){
        UserAccount mUserAccount = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + GDefine.TABLE_NAME_USERACCOUNT + " ORDER BY create_date DESC", null);
        if(cursor.moveToFirst()){
            mUserAccount = getRecord(cursor);
        }
        cursor.close();
        return mUserAccount;
    }
}
