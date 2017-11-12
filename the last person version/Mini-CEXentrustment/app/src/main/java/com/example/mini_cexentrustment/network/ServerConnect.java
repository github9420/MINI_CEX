package com.example.mini_cexentrustment.network;

import android.content.Context;
import android.util.Log;

import com.example.mini_cexentrustment.common.DateTool;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.GDefine;
import com.example.mini_cexentrustment.define.UserAccount;

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

    public static String post(String addr, Map<String, String> params, CommandType qType, Context context) throws IOException, JSONException {
        String responseCode = "-1";
        URL url;
        try {
            url = new URL(addr);
            Log.e(TAG,addr);
        } catch (MalformedURLException e) {
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
                http.disconnect();
            }
        }
        Log.i(TAG,responseCode);
        return responseCode;
    }

    //參考:http://xxs4129.pixnet.net/blog/post/162273853-android%E4%BD%BF%E7%94%A8httpclient%E2%80%8B%E8%88%87httpurlconnection%E2%80%8B%E9%80%A3%E7%B7%9A%E2%80%8B
    public static String changeInputStream(InputStream inputStream) {    //將輸入串流轉成字串回傳
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
        return result;                //回傳result
    }

    public static String dispatchCommandType(String jsonData, CommandType type, Context context) throws JSONException {
        Log.e(TAG,"aaaaa");
        String result  = "1";
        JSONObject jsonObject = new JSONObject(jsonData);
        switch(type)
        {
            case account_user_authentication:
                if(jsonObject.get("result").equals("true")){
                    Log.e(TAG,"dddd");
                    String token = jsonObject.get("token").toString();
                    String account = jsonObject.get("account").toString();
                    String userId = jsonObject.get("userId").toString();
                    String phoneId = jsonObject.get("phoneId").toString();
                    //Log.d(TAG,"token:"+token+"account:"+account+"userId:"+"phoneID"+phoneId);
                    Log.d(TAG,"token:"+token);
                    Log.d(TAG,"account:"+account);
                    Log.d(TAG,"userId:"+userId);
                    Log.d(TAG,"phoneId:"+phoneId);



                    /*
                    mToken = token;
                    try {
                        // 再次進行登入
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("account", "upstairs0102@gmail.com");
                        map.put("userPassword", "1234");
                        map.put("phoneId", "");
                        result = post(GDefine.account_user_login, map, type, context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }else{
                    result = "-1";
                }
                break;
            case account_user_login:
                if(jsonObject.get("result").equals("true")){
                    String userAuth = jsonObject.get("userAuth").toString(); //權限	system:系統管理者    standard:一般    trail:試用
                    String userType = jsonObject.get("userType").toString(); //身份別	manager:管理者    member:成員
                    String email = jsonObject.get("email").toString(); //email
                    String userName = jsonObject.get("userName").toString(); //姓名
                    String groupNo = jsonObject.get("groupSNo").toString(); //團體編號
                    String groupName = jsonObject.get("groupName").toString(); //團體名稱
                    String LoginRole = jsonObject.get("groupName").toString(); //目前登入角色
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
                    mUserAccount.setUserAuth(userAuth);
                    mUserAccount.setUserType(userType);
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
                        result = "-1";
                    }else{
                        if(mUserAccount.getNeedChangePassword().equals("true")){
                            result = "1"; //需改密碼
                        }else{
                            result = "2"; //正常登入
                        }

                    }
                }else{
                    result = "-1";
                }
                break;
            case account_user_sign_up:
                if(jsonObject.get("result").equals("true")){
                    result = "1";
                }else{
                    String status = jsonObject.get("status").toString(); //狀態	accountHasBeenUsed: 帳號已註冊: 帳號已註冊 groupAlreadyExist: 組織已註冊
                    if(status.equals("accountHasBeenUsed")){
                        //帳號已註冊
                        result = "-3";

                    }else if (status.equals("groupAlreadyExist")){
                        //組織已註冊
                        result = "-2";
                    }else{

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
            default:

                break;
        }
        return result;
    }
}
