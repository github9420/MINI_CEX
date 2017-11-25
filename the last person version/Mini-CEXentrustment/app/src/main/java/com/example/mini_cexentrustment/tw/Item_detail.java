package com.example.mini_cexentrustment.tw;

import android.util.Log;

import com.example.mini_cexentrustment.network.ServerConnect;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rorensu on 2017/11/20.
 */

public class Item_detail {
    private static final String TAG = Item_detail.class.getSimpleName();
    public String documentSNo="";
    public String subject="臨床評量";
    public String teachername;
    public String sutdentName;
    public String evaluateDateTime;
    public String status;

    public Item_detail(JSONObject jsonObject)
    {
        if (jsonObject != null)
        {
            try
            {
                sutdentName = jsonObject.get("studentName").toString(); //學生新聞:訊息類別
                teachername = jsonObject.get("teacherName").toString(); // 學生新聞:內容
                teachername=teachername+"-"+sutdentName;
                evaluateDateTime = jsonObject.get("evaluateDateTime").toString(); //學生新聞:表單流水號
                status = jsonObject.get("status").toString(); //學生新聞:日期
                documentSNo= jsonObject.get("documentSNo").toString();


                Log.i(TAG,"teachername:"+teachername);
                Log.i(TAG,"evaluateDateTime:"+evaluateDateTime);
                Log.i(TAG,"status:"+status);
            }
            catch (JSONException e)
            {
                Log.w("ERRROR", e);
            }
        }else{
            Log.e(TAG,"null");
        }
    }
    public String getStatus(){
        return status;
    }
    public String getDocumentSNo(){
        return documentSNo;
    }
    public String getevaluateDateTime(){
        return evaluateDateTime;
    }
}
