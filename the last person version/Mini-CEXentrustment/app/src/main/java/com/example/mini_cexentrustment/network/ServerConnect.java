package com.example.mini_cexentrustment.network;

import android.content.Context;
import android.util.Log;

import com.example.mini_cexentrustment.common.DateTool;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.GDefine;
import com.example.mini_cexentrustment.define.UserAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 信威 on 2017/10/23.
 */
public class ServerConnect {
    private static final String TAG = ServerConnect.class.getSimpleName();
    private static UserAccountDAO mUserAccountDAO;
    private static String mToken = "";
    public static String Statisc_imformation="";
    public static String detail_imformation="";
    public static Boolean setting_imformation=false;
    public static Boolean logout_imformation=false;
    public static String Test = "";
    public static Boolean F_logout_flag=false;
    public static Boolean F_statisc_flag=false;
    public static Boolean F_detail_flag=false;
    public static Boolean F_setting_flag=false;
    public static JSONArray jsonArray;
    public static Boolean fuck = false, fuck1 = false, fuck2 = false;
    public static Boolean student_get_request_list_b = false,student_get_request_setting_b = false,student_get_evaluation_record_b = false;

    public static String post(String addr, Map<String, String> params, CommandType qType, Context context) throws IOException, JSONException {

        String responseCode = "-1";
        URL url;
        try {
            url = new URL(addr);
            Log.e(TAG,addr);
        } catch (MalformedURLException e) {
            Log.e(TAG,"wrong url"+addr);
            throw new IllegalArgumentException("invalid url: " + addr);
        }
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        JSONObject body = new JSONObject();
//        JSONObject data = new JSONObject();
//        JSONArray data = new JSONArray();
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            body.put(param.getKey(), param.getValue());
        }
//        data.put("data", body);
//        data.put(body);

        Log.v(TAG, "Posting : '" + body + "' to " + url);
//        Log.v(TAG, "Posting '" + data + "' to " + url);
        byte[] bytes = body.toString().getBytes("UTF-8");
//        byte[] bytes = data.toString().getBytes("UTF-8");
        HttpURLConnection http = null;
        try {
            Log.e("URL", "> " + url);
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(3000);

            http.setDoInput(true);

            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setUseCaches(false);

            http.setFixedLengthStreamingMode(bytes.length);
            //http.setRequestProperty("Host", GDefine.host);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("charset", "UTF-8");
            http.setRequestProperty("Content-Length", Integer.toString(bytes.length));
            OutputStream out = http.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
            BufferedWriter writer = new BufferedWriter(outWriter);
            writer.write(body.toString());
            writer.flush();
            byte[] str = writer.toString().getBytes();
            Log.d(TAG, new String(str, "UTF-8"));
            out.close();
            http.connect();
            Log.d(TAG,http.toString());
            if(http != null) {
                responseCode = String.valueOf(http.getResponseCode());
                Log.e(TAG,responseCode);
                if(Integer.valueOf(responseCode) == HttpURLConnection.HTTP_OK) {
                    // Correct password
                    //取得回傳的inputStream (輸入流串)
                    InputStream inputStream = http.getInputStream();
                    responseCode = dispatchCommandType(changeInputStream(inputStream), qType, context);

                } else {
                    throw new IOException("Post failed with error code : " + responseCode);

                }
            }
//            InputStream resultStream = http.getInputStream();
//            InputStream inStream = http.getErrorStream();       post
//            Map<String, List<String>> map = http.getHeaderFields();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (http != null) {
                Log.e(TAG,"disconnect");
                http.disconnect();
            }
        }
        Log.i(TAG,responseCode);
        return responseCode;
    }

    //參考:http://xxs4129.pixnet.net/blog/post/162273853-android%E4%BD%BF%E7%94%A8httpclient%E2%80%8B%E8%88%87httpurlconnection%E2%80%8B%E9%80%A3%E7%B7%9A%E2%80%8B
    public static String changeInputStream(InputStream inputStream) {    //將輸入串流轉成字串回傳
        Log.e(TAG,"step into");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //ByteArrayOutputStream型態可不斷寫入來增長緩存區,可使用toByteArray()、toString()獲取數據
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {        //判斷inputStream是否非空字串
            try {
                while ((len = inputStream.read(data)) != -1) {    //將inputStream寫入data並回傳字數到len
                    outputStream.write(data, 0, len);            //將data寫入到輸出流中,參數二為起始位置,len是讀取長度
                }
                result = new String(outputStream.toByteArray(), GDefine.encode);    //resilt取得outputStream的string並轉成encode邊碼
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("text", "Http_Client.changeInputStream.IOException="+e.toString());
            }
        }
        Log.e(TAG,"safe come out, result="+result);
        return result;                //回傳result
    }

    public static String dispatchCommandType(String jsonData, CommandType type, Context context) throws JSONException {

        //Liu
        Log.i(TAG,"dispatchCommandType01");
        String result  = "1";
        JSONObject jsonObject = null;
        JSONArray jsarray_result_list;
        JSONArray jsonArray = null;
        if(type==CommandType.student_get_news){
            Test = jsonData;
        } else if(type==CommandType.student_get_request_list||type==CommandType.teacher_request_list || type == CommandType.student_get_evaluation_record ||type == CommandType.teacher_request_record){
            Test = jsonData;
        }else if(type ==CommandType.student_get_request_setting){
            Test = jsonData;
            jsonArray = new JSONArray(jsonData);
        }
        else {
            jsonObject = new JSONObject(jsonData);
        }


//Original 2017/11/17
//        String result  = "1";
//        JSONObject jsonObject = new JSONObject(jsonData);
//        Log.e(TAG,"dispatchCommandType");

        switch(type)
        {
            case account_user_authentication:
                if(jsonObject.get("result").equals("true")){

                    String token = jsonObject.get("token").toString();
                    String account = jsonObject.get("account").toString();
                    String userId = jsonObject.get("userId").toString();
                    String phoneId = jsonObject.get("phoneId").toString();
                    //Log.d(TAG,"token:"+token+"account:"+account+"userId:"+"phoneID"+phoneId);
                    Log.d(TAG,"token:"+token);
                    Log.d(TAG,"account:"+account);
                    Log.d(TAG,"userId:"+userId);
                    Log.d(TAG,"phoneId:"+phoneId);


                    mToken = token;
                    try {
                        // 再次進行登入
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userId", userId);
                        map.put("token", token);
                        map.put("phoneId", phoneId);
                        result = post(GDefine.account_user_login, map, CommandType.account_user_login, context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    result = "-1";
                }
                break;
            case account_user_login:
                Log.e(TAG,"account_user_login");
                if(jsonObject.get("result").equals("true")){
                    //String userAuth = jsonObject.get("userAuth").toString(); //權限	system:系統管理者    standard:一般    trail:試用
                    //String userType = jsonObject.get("userType").toString(); //身份別	manager:管理者    member:成員
                    String email = jsonObject.get("email").toString(); //email
                    String userName = jsonObject.get("userName").toString(); //姓名
                    String groupNo = jsonObject.get("groupSNo").toString(); //團體編號
                    String groupName = jsonObject.get("groupName").toString(); //團體名稱
                    String LoginRole = jsonObject.get("loginRole").toString(); //目前登入角色
                    String mobilePhone = jsonObject.get("mobilePhone").toString(); //手機號碼
                    String phone = jsonObject.get("phone").toString(); //電話號碼
                    String phoneEx = jsonObject.get("phoneEx").toString(); //分機號碼
                    String token = jsonObject.get("token").toString();//驗證碼
                    String userId = jsonObject.get("userId").toString();
                    String needChangePassword = jsonObject.get("needChangePassword").toString();

                    if(mUserAccountDAO == null){

                        mUserAccountDAO = new UserAccountDAO(context);
                    }
                    UserAccount mUserAccount = new  UserAccount();
                    mUserAccount.setUserId(userId);
                    //mUserAccount.setUserAuth(userAuth);
                    //mUserAccount.setUserType(userType);
                    mUserAccount.setEmail(email);
                    mUserAccount.setUserName(userName);
                    mUserAccount.setGroupSNo(Integer.valueOf(groupNo));
                    mUserAccount.setGroupName(groupName);
                    mUserAccount.setLoginRole(LoginRole);
                    mUserAccount.setMobilePhone(mobilePhone);
                    mUserAccount.setPhoneEX(phoneEx);
                    mUserAccount.setPhone(phone);
                    mUserAccount.setToken(token);
                    mUserAccount.setNeedChangePassword(needChangePassword);
                    mUserAccount.setCreateDate(DateTool.getDateTimeByNow());
                    mUserAccount =  mUserAccountDAO.insert(mUserAccount);
                    if(mUserAccount.getId() == 0){
                        Log.e(TAG,"fuck i'm here");
                        result = "-1";
                    }else{
                        if(mUserAccount.getNeedChangePassword().equals("true")){
                            Log.e(TAG,"fuck i'm here2");
                            result = "2"; //需改密碼
                        }else{
                            Log.e(TAG,"Normal login !");
                            result = "1"; //正常登入
                        }
                    }
                }else{

                    result = "-1";
                }
                break;
            case account_user_sign_up:
                Log.i(TAG,"get into sign up");
                Log.i(TAG,result);
                if(jsonObject.get("result").equals("true")){
                    String status = jsonObject.get("token").toString();
                    Log.d(TAG,"status:"+status);
                    result = "1";
                }else{
                    String status = jsonObject.get("status").toString(); //狀態	accountHasBeenUsed: 帳號已註冊: 帳號已註冊 groupAlreadyExist: 組織已註冊
                    Log.e(TAG,status);
                    if(status.equals("accountHasBeenUsed")){
                        //帳號已註冊
                        Log.e(TAG,"accountHasBeenUsed");
                        result = "-3";

                    }else if (status.equals("groupAlreadyExist")){
                        //組織已註冊
                        Log.e(TAG,"groupAlreadyExist");
                        result = "-2";
                    }else{
                        Log.e(TAG,"No idea");
                        result = "-1";
                    }
                }
                break;
            case account_send_setting_password_email:
                if(jsonObject.get("result").equals("true")){
                    result = "1";
                }else{
                    String status = jsonObject.get("status").toString(); //狀態	emailDoesNotExist: 信箱不存在
                    if(status.equals("emailDoesNotExist")){
                        //信箱不存在
                        result = "-2";

                    }else{

                        result = "-1";
                    }
                }
                break;
            case account_modify_user_password:

                break;
            case modify_login_role:

                break;
            case account_get_user_info:

                break;
            case student_get_news:
                Log.e(TAG,"student_get_news");
                jsonArray = new JSONArray(jsonData);
                //JSONObject jsonObject = new JSONObject(jsonData);
                //JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    //populate arraylist with json array data
                    jsonObject = jsonArray.getJSONObject(i);
                    Log.i(TAG,"get into student get news");
                    String news_content = jsonObject.get("content").toString(); // 學生新聞:內容
                    String news_newsType = jsonObject.get("newsType").toString(); //學生新聞:訊息類別
                    String news_documentSNo = jsonObject.get("documentSNo").toString(); //學生新聞:表單流水號
                    String news_dateTime = jsonObject.get("dateTime").toString(); //學生新聞:日期
                    Log.i(TAG,"news_content:"+news_content);
                    Log.i(TAG,"news_newsType:"+news_newsType);
                    Log.i(TAG,"news_documentSNo:"+news_documentSNo);
                    Log.i(TAG,"news_dataTime:"+news_dateTime);

                }

                fuck = true;
                break;
            case student_get_request_list:
                Log.i(TAG,"get into student_get_request_list");
                jsonArray = new JSONArray(jsonData);

                for (int i = 0; i < jsonArray.length(); i++) {

                    jsonObject = jsonArray.getJSONObject(i);
                    String request_documentSNo = jsonObject.get("documentSNo").toString(); // 學生新聞:內容
                    String request_GoupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
                    String request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
                    String request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
                    String request_teacherName = jsonObject.get("teacherName").toString(); //學生新聞:日期
                    String request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:日期
                    String request_studentName = jsonObject.get("studentName").toString(); //學生新聞:日期
                    String request_status = jsonObject.get("status").toString(); //學生新聞:日期
                    String request_modifyDateTime = jsonObject.get("modifyDateTime").toString(); //學生新聞:日期
                    Log.i(TAG,"request_documentSNo:"+request_documentSNo);
                    Log.i(TAG,"request_GoupSNo:"+request_GoupSNo);
                    Log.i(TAG,"request_teacherUserId:"+request_teacherUserId);
                    Log.i(TAG,"request_GroupName:"+request_GroupName);
                    Log.i(TAG,"request_teacherName:"+request_teacherName);
                    Log.i(TAG,"request_studentUserId:"+request_studentUserId);
                    Log.i(TAG,"request_studentName:"+request_studentName);
                    Log.i(TAG,"request_status"+request_status);
                    Log.i(TAG,"request_modifyDateTime"+request_modifyDateTime);
                }
                student_get_request_list_b = true;
                break;
            case student_get_request_setting:
                Log.i(TAG,"get into student_get_request_setting");
                jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String request_userId = jsonObject.get("userId").toString(); // 學生新聞:內容
                    String request_userName = jsonObject.get("userName").toString(); // 學生新聞:內容
                    Log.i(TAG,"request_userId:"+request_userId);
                    Log.i(TAG,"request_userName:"+request_userName);
                }
                student_get_request_setting_b = true;
                break;
            case student_get_evaluation_record:
                Log.i(TAG,"student_get_evaluation_record");
                jsonObject = new JSONObject(jsonData);
                Log.i(TAG,jsonData);
                //JSONObject jsonObject = new JSONObject(jsonData);
                Log.i(TAG, "get into teach request record");
                Log.i(TAG, Test);
                String student_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
                String student_request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
                String student_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
                String student_request_teacherUserName = jsonObject.get("teacherUserName").toString(); // 學生新聞:內容
                String student_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
                String student_request_studentUserName = jsonObject.get("studentUserName").toString(); //學生新聞:表單流水號
                String student_request_teacherType = jsonObject.get("teacherType").toString(); //學生新聞:日期
                String student_request_studentType = jsonObject.get("studentType").toString(); //學生新聞:日期

                String student_request_MedicalNumber = jsonObject.get("medicalNumber").toString(); //學生新聞:訊息類別
                String student_request_Division = jsonObject.get("division").toString(); //學生新聞:表單流水號
                String student_request_Diagnosis = jsonObject.get("diagnosis").toString(); //學生新聞:日期
                String student_request_item1_1 = jsonObject.get("item1_1").toString(); // 學生新聞:內容
                String student_request_item1_2 = jsonObject.get("item1_2").toString(); //學生新聞:訊息類別
                String student_request_item2_1 = jsonObject.get("item2_1").toString(); //學生新聞:表單流水號
                String student_request_item2_2 = jsonObject.get("item2_2").toString(); //學生新聞:日期
                String student_request_item2_3 = jsonObject.get("item2_3").toString(); //學生新聞:日期
                String student_request_item2_4 = jsonObject.get("item2_4").toString(); //學生新聞:訊息類別
                String student_request_item2_5 = jsonObject.get("item2_5").toString(); //學生新聞:表單流水號
                String student_request_item2_6 = jsonObject.get("item2_6").toString(); //學生新聞:日期
                String student_request_Comment = jsonObject.get("comment").toString(); // 學生新聞:內容
                String student_request_Status = jsonObject.get("status").toString(); //學生新聞:日期
                String student_request_RequestDateTime = jsonObject.get("requestDateTime").toString(); //學生新聞:表單流水號
                String student_request_EvaluateDateTime = jsonObject.get("evaluateDateTime").toString(); //學生新聞:日期
                String student_request_ModifyDateTime = jsonObject.get("modifyDateTime").toString(); //學生新聞:日期

                Log.i(TAG, "student_request_Comment:" + student_request_Comment);
                Log.i(TAG, "student_request_Diagnosis:" + student_request_Diagnosis);
                Log.i(TAG, "student_request_RequestDateTime:" + student_request_RequestDateTime);
                Log.i(TAG, "student_request_item2_4:" + student_request_item2_4);
                Log.i(TAG, "student_request_EvaluateDateTime:" + student_request_EvaluateDateTime);
                Log.i(TAG, "student_request_ModifyDateTime:" + student_request_ModifyDateTime);

                student_get_evaluation_record_b = true;
                break;
            case student_get_evaluation_request_evaluation:
                jsonObject = new JSONObject(jsonData);
                if(jsonObject.get("result").equals("true")){
                    Log.i(TAG, "true");
                    String status = jsonObject.get("status").toString();
                    Log.d(TAG,"status:"+status);

                }else{
                    Log.i(TAG, "false");
                    result = "-1";
                }
                break;
            case teacher_request_list:
                Log.e(TAG,"teacher_request_list getin");
                //jsonObject = new JSONObject(jsonData);
                jsonArray = new JSONArray(jsonData);
                Log.i(TAG,jsonData);
                //JSONObject jsonObject = new JSONObject(jsonData);
                //JSONArray jsonArray = jsonObject.getJSONArray("data");


                for (int i = 0; i < jsonArray.length(); i++) {

                    //populate arraylist with json array data
                    jsonObject = jsonArray.getJSONObject(i);
                    Log.i(TAG,"get into teach request list");
                    Log.i(TAG,Test);
                    String teacher_request_documentSNo = jsonObject.get("documentSNo").toString(); // 學生新聞:內容
                    String teacher_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
                    String teacher_request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
                    String teacher_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
                    String teacher_request_teacherName = jsonObject.get("teacherName").toString(); // 學生新聞:內容
                    String teacher_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
                    String teacher_request_studentName = jsonObject.get("studentName").toString(); //學生新聞:表單流水號
                    String teacher_request_status = jsonObject.get("status").toString(); //學生新聞:日期
                    String teacher_request_requestDateTime = jsonObject.get("requestDateTime").toString(); //學生新聞:日期
                    Log.i(TAG,"teacher_request_documentSNo:"+teacher_request_documentSNo);
                    Log.i(TAG,"teacher_request_GroupSNo:"+teacher_request_GroupSNo);
                    Log.i(TAG,"teacher_request_GroupName:"+teacher_request_GroupName);
                    Log.i(TAG,"teacher_request_teacherUserId:"+teacher_request_teacherUserId);
                    Log.i(TAG,"teacher_request_teacherName:"+teacher_request_teacherName);
                    Log.i(TAG,"teacher_request_studentUserId:"+teacher_request_studentUserId);
                    Log.i(TAG,"teacher_request_studentName:"+teacher_request_studentName);
                    Log.i(TAG,"teacher_request_status:"+teacher_request_status);
                    Log.i(TAG,"teacher_request_requestDateTime:"+teacher_request_requestDateTime);
                }
                fuck1 = true;
                break;
            case teacher_request_record:
                jsonArray = new JSONArray(jsonData);
                Log.i(TAG,jsonData);
                //JSONObject jsonObject = new JSONObject(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {

                    //populate arraylist with json array data
                    jsonObject = jsonArray.getJSONObject(i);
                    Log.i(TAG, "get into teach request record");
                    Log.i(TAG, Test);
                    String teacher_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
                    String teacher_request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
                    String teacher_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
                    String teacher_request_teacherUserName = jsonObject.get("teacherUserName").toString(); // 學生新聞:內容
                    String teacher_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
                    String teacher_request_studentUserName = jsonObject.get("studentUserName").toString(); //學生新聞:表單流水號
                    String teacher_request_teacherType = jsonObject.get("teacherType").toString(); //學生新聞:日期
                    String teacher_request_studentType = jsonObject.get("studentType").toString(); //學生新聞:日期

                    Log.i(TAG, "teacher_request_GroupSNo:" + teacher_request_GroupSNo);
                    Log.i(TAG, "teacher_request_GroupName:" + teacher_request_GroupName);
                    Log.i(TAG, "teacher_request_teacherUserId:" + teacher_request_teacherUserId);
                    Log.i(TAG, "teacher_request_teacherUserName:" + teacher_request_teacherUserName);
                    Log.i(TAG, "teacher_request_studentUserId:" + teacher_request_studentUserId);
                    Log.i(TAG, "teacher_request_studentUserName:" + teacher_request_studentUserName);
                    Log.i(TAG, "teacher_request_teacherType:" + teacher_request_teacherType);
                    Log.i(TAG, "teacher_request_studentType:" + teacher_request_studentType);
                }
                fuck2 = true;
                break;
                //Rorensu
            case student_get_calculation_result:

                String name="";
                Log.e(TAG,"student_get_calculation_result");
                 //jsonArray = new JSONArray(jsonData);
                jsonObject = new JSONObject(jsonData);
                String teacherName = jsonObject.get("teacherName").toString(); //
                String evaluationDatePeriod = jsonObject.get("evaluationDatePeriod").toString(); //
                Log.i(TAG,"teacherName:"+teacherName);
                Log.i(TAG,"evaluationDatePeriod:"+evaluationDatePeriod);
                JSONArray jsonArrayS = new JSONArray(jsonObject.getString("calculationResult"));


                for (int i = 0; i < jsonArrayS.length(); i++) {
                    JSONObject object = (JSONObject) jsonArrayS.get(i);
                    int id = object.getInt("value");
                    name = object.getString("itemName");
                    Statisc_imformation=Statisc_imformation+name+" "+Integer.toString(id)+"\n";

                    Log.e(TAG,name+id);
                }
                Log.e(TAG,"student_get_calculation_result end");
                result= Statisc_imformation;
                F_statisc_flag=true;
                break;
                //Rorensu
            case teacher_get_calculation_result:
                String nameT="";
                Log.e(TAG,"teacher_get_calculation_result");
                //jsonArray = new JSONArray(jsonData);
                jsonObject = new JSONObject(jsonData);
                String teacherNameT = jsonObject.get("teacherName").toString(); //
                String evaluationDatePeriodT = jsonObject.get("evaluationDatePeriod").toString(); //
                Log.i(TAG,"teacherName:"+teacherNameT);
                Log.i(TAG,"evaluationDatePeriod:"+evaluationDatePeriodT);
                JSONArray jsonArrayT = new JSONArray(jsonObject.getString("calculationResult"));


                for (int i = 0; i < jsonArrayT.length(); i++) {
                    JSONObject object = (JSONObject) jsonArrayT.get(i);
                    int id = object.getInt("value");
                    nameT = object.getString("itemName");
                    Statisc_imformation=Statisc_imformation+nameT+" "+Integer.toString(id)+"\n";

                    Log.e(TAG,nameT+id);
                }
                Log.e(TAG,"teacher_get_calculation_result end");
                result= Statisc_imformation;
                F_statisc_flag=true;
                break;
            case student_get_result_list:
                Log.e(TAG,"student_get_result_list");
                detail_imformation=jsonData;
                F_detail_flag=true;
                /*jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {
                    //populate arraylist with json array data
                    jsonObject = jsonArray.getJSONObject(i);
                    String teachername = jsonObject.get("teacherName").toString(); // 學生新聞:內容
                    String sutdentName = jsonObject.get("studentName").toString(); //學生新聞:訊息類別
                    String evaluateDateTime = jsonObject.get("evaluateDateTime").toString(); //學生新聞:表單流水號
                    String status = jsonObject.get("status").toString(); //學生新聞:日期
                    Log.i(TAG,"teachername:"+teachername);
                    Log.i(TAG,"sutdentName:"+sutdentName);
                    Log.i(TAG,"evaluateDateTime:"+evaluateDateTime);
                    Log.i(TAG,"status:"+status);
                }
                Log.e(TAG,"student_get_result_list end");*/
                break;
            case student_evaluation_fill_evaluation_info:
                Log.e(TAG,"student_evaluation_fill_evaluation_info");
                if(jsonObject.get("result").equals("true")){
                    String status = jsonObject.get("status").toString(); //狀態	emailDoesNotExist: 信箱不存在
                    Log.e(TAG,status);
                }else{
                    result = "1";
                }
                break;
            case teacher_get_result_list:
                Log.e(TAG,"teacher_get_result_list");
                detail_imformation=jsonData;
                F_detail_flag=true;
                break;
            case student_modify_user_info:
                if(jsonObject.get("result").equals("true")){
                    setting_imformation=true;

                }else{
                    result = "-1";
                }
                F_setting_flag=true;
                break;
            case teacher_modify_user_info:
                if(jsonObject.get("result").equals("true")){
                    setting_imformation=true;

                }else{
                    result = "-1";
                }
                F_setting_flag=true;
                break;

            case teacher_user_logout:
                if(jsonObject.get("result").equals("true")){
                    logout_imformation=true;

                }else{
                    result = "-1";
                }
                F_logout_flag=true;
                break;
            case student_user_logout:
                if(jsonObject.get("result").equals("true")){
                    logout_imformation=true;

                }else{
                    result = "-1";
                }
                F_logout_flag=true;
                break;
            default:

                break;
        }
        return result;
    }
}
