package com.example.mini_cexentrustment.define;

import android.content.Context;

import java.util.Map;

/**
 * Created by 信威 on 2017/8/3.
 */
public interface IParam {
    public  void setActiveContext(Context context);       //設定當下執行 activity Context
    public  void setCommandType(CommandType type);        //設定觸發命令類別
    public  void initJSONObject(Map<String, String> map); //設定交易資料物件(JSON格式)
}
