package com.example.mini_cexentrustment.thread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.GDefine;
import com.example.mini_cexentrustment.define.IParam;
import com.example.mini_cexentrustment.dialog.Notice;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.tw.ForgetPasswordActivity;
import com.example.mini_cexentrustment.tw.LoginActivity;
import com.example.mini_cexentrustment.tw.RegisteredActivity;
import com.example.mini_cexentrustment.tw.ResetPassword;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 信威 on 2017/5/31.
 * 執行緒控制驅動
 */
public class NetTask extends AsyncTask<Void, Void, String> implements IParam {
    private Context mContext;
    private Map<String, String> parameters = null;;
    private CommandType qType;

    @Override
    public void setActiveContext(Context context) {
        // TODO
        this.mContext = context;
    }

    @Override
    public void setCommandType(CommandType type) {
        // TODO
        this.qType = type;
    }

    @Override
    protected void onPreExecute() {
        // TODO
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String status = "-1";
        try {
            String url = dispatchUrl(this.qType);
            if(qType == CommandType.account_user_authentication) {
                status = ServerConnect.post(url, parameters, qType, mContext);
            }else if(qType == CommandType.account_modify_user_password){
                status = ServerConnect.post(url, parameters, qType, mContext);
            }else if(qType == CommandType.account_user_sign_up){
                status = ServerConnect.post(url, parameters, qType, mContext);
            }else if(qType == CommandType.account_send_setting_password_email){
                status = ServerConnect.post(url, parameters, qType, mContext);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(final String result) {
        // TODO
        super.onPostExecute(result);
        if(result == "-1") {
            if(mContext instanceof LoginActivity){
                if (qType == CommandType.account_user_authentication) {
                    Notice.setHandler((LoginActivity) mContext);
                    Notice.popLoginErrorMessage(result);
                }else {
                    Log.d("LoginActivity", "異常");
                }
            }else if(mContext instanceof ResetPassword){
                if (qType == CommandType.account_modify_user_password) {
                    Notice.setHandler((ResetPassword) mContext);
                    Notice.popResetPasswordErrorMessage();
                }else {
                    Log.d("ResetPassword", "異常");
                }
            }else if(mContext instanceof RegisteredActivity){
                if (qType == CommandType.account_user_sign_up) {
                    Notice.setHandler((RegisteredActivity) mContext);
                    Notice.popRegisteredErrorMessage();
                }else {
                    Log.d("RegisteredActivity", "異常");
                }
            }else if(mContext instanceof ForgetPasswordActivity){
                if (qType == CommandType.account_send_setting_password_email) {
                    Notice.setHandler((ForgetPasswordActivity) mContext);
                    Notice.popForgetPasswordErrorMessage();
                }else {
                    Log.d("ForgetPasswordActivity", "異常");
                }
            }
        }else {
            if(mContext instanceof LoginActivity){
                if (qType == CommandType.account_user_authentication) {
                    Notice.setHandler((LoginActivity) mContext);
                    Notice.popLoginSuccessMessage(result);
                }
            }else if(mContext instanceof ResetPassword){
                if (qType == CommandType.account_modify_user_password) {
                    Notice.setHandler((ResetPassword) mContext);
                    Notice.popResetPasswordSuccessMessage(result);
                }

            }else if(mContext instanceof RegisteredActivity){
                if (qType == CommandType.account_user_sign_up) {
                    Notice.setHandler((RegisteredActivity) mContext);
                    if(result == "-2") {
                        Notice.popRegisteredErrorMessageByGroupAlreadyExist();
                    }else if(result == "-3") {
                        Notice.popRegisteredErrorMessageByAccountHasBeenUsed();
                    }else {
                        Notice.popRegisteredSuccessMessage(result);
                    }
                }else {
                    Log.d("ResetPassword", "異常");
                }
            }else if(mContext instanceof ForgetPasswordActivity){
                if (qType == CommandType.account_send_setting_password_email) {
                    Notice.setHandler((ForgetPasswordActivity) mContext);
                    if(result == "-2") {
                        Notice.popForgetPasswordErrorMessageByEmailDoesNotExist();
                    }else {
                        Notice.popForgetPasswordSuccessMessage(result);
                    }
                }else {
                    Log.d("ForgetPasswordActivity", "異常");
                }
            }
        }
        parameters.clear();
        parameters = null;
    }

    private String dispatchUrl(CommandType type) {
        String url = null;
        switch(type)
        {
            case account_user_authentication:
                url = GDefine.account_user_authentication; // 先驗證
                break;
            case account_user_sign_up:
                url = GDefine.account_user_sign_up;
                break;
            case account_send_setting_password_email:
               url = GDefine.account_send_setting_password_email;
                break;
            case account_modify_user_password:
                url = GDefine.account_modify_user_password;
                break;
            case modify_login_role:
                url = GDefine.modify_login_role;
                break;
            case account_get_user_info:
                url = GDefine.account_get_user_info;
                break;
            default:
                url ="";
                break;
        }
        return url;
    }

    @Override
    public void initJSONObject(Map<String, String> map) {
        // TODO
        this.parameters = map;
    }
}
