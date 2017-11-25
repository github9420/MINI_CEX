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
    private final static String TAG = NetTask.class.getSimpleName();
    private Context mContext;
    private Map<String, String> parameters = null;
    ;
    private CommandType qType;

    @Override
    public void setActiveContext(Context context) {
        // TODO
        Log.i("NetTask", "setActiveContext");
        this.mContext = context;
    }

    @Override
    public void setCommandType(CommandType type) {
        // TODO
        Log.i("NetTask", "setCommandType");
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
            if (qType == CommandType.account_user_authentication) {
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.account_modify_user_password) {
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.account_user_sign_up) {
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.account_send_setting_password_email) {
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_get_news) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_get_calculation_result) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.teacher_get_calculation_result) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_get_evaluation_record) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_get_result_list) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.teacher_get_result_list) {
                Log.e(TAG, "doInBackground:" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_get_request_list) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            } else if (qType == CommandType.student_get_request_setting) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            } else if (qType == CommandType.teacher_request_list) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }else if(qType == CommandType.student_get_evaluation_request_evaluation){
                Log.i("NetTask","sdsdsdsd" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.teacher_request_record) {
                Log.i("NetTask", "testtest" + url);
                status = ServerConnect.post(url, parameters, qType, mContext);
            } else if (qType == CommandType.student_modify_user_info) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            } else if (qType == CommandType.student_evaluation_fill_evaluation_info) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            } else if (qType == CommandType.teacher_modify_user_info) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            } else if (qType == CommandType.student_user_logout) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }else if (qType == CommandType.teacher_get_news) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }else if (qType == CommandType.teacher_evaluation_reject_request) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }
            else if (qType == CommandType.teacher_user_logout) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }else if(qType == CommandType.teacher_add_evaluation_record){
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }
            else if (qType == CommandType.transfer_wav_to_text_by_based64) {
                status = ServerConnect.post(url, parameters, qType, mContext);
                Log.e(TAG, "doInBackground:" + url);
            }else {
                Log.e(TAG, "doInBackground is wrong"+this.qType);
            }

        } catch (IOException e) {
            Log.e(TAG, "doInBackground io is wrong");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "doInBackground json is wrong");
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(final String result) {
        // TODO
        super.onPostExecute(result);
        Log.i("return answer =", result);
        if (result == "-1") {
            if (mContext instanceof LoginActivity) {
                if (qType == CommandType.account_user_authentication) {
                    Notice.setHandler((LoginActivity) mContext);
                    Notice.popLoginErrorMessage(result);
                } else {
                    Log.d("LoginActivity", "異常");
                }
            } else if (mContext instanceof ResetPassword) {
                if (qType == CommandType.account_modify_user_password) {
                    Notice.setHandler((ResetPassword) mContext);
                    Notice.popResetPasswordErrorMessage();
                } else {
                    Log.d("ResetPassword", "異常");
                }
            } else if (mContext instanceof RegisteredActivity) {
                if (qType == CommandType.account_user_sign_up) {
                    Notice.setHandler((RegisteredActivity) mContext);
                    Notice.popRegisteredErrorMessage();
                } else {
                    Log.d("RegisteredActivity", "異常");
                }
            } else if (mContext instanceof ForgetPasswordActivity) {
                if (qType == CommandType.account_send_setting_password_email) {
                    Notice.setHandler((ForgetPasswordActivity) mContext);
                    Notice.popForgetPasswordErrorMessage();
                } else {
                    Log.d("ForgetPasswordActivity", "異常");
                }
            } else {
                Log.e(TAG, "onPostExecute error");
            }
        } else {
            if (mContext instanceof LoginActivity) {
                if (qType == CommandType.account_user_authentication) {
                    Notice.setHandler((LoginActivity) mContext);
                    Notice.popLoginSuccessMessage(result);
                }
            } else if (mContext instanceof ResetPassword) {
                if (qType == CommandType.account_modify_user_password) {
                    Notice.setHandler((ResetPassword) mContext);
                    Notice.popResetPasswordSuccessMessage(result);
                }

            } else if (mContext instanceof RegisteredActivity) {
                if (qType == CommandType.account_user_sign_up) {
                    Notice.setHandler((RegisteredActivity) mContext);
                    if (result == "-2") {
                        Notice.popRegisteredErrorMessageByGroupAlreadyExist();
                    } else if (result == "-3") {
                        Notice.popRegisteredErrorMessageByAccountHasBeenUsed();
                    } else {
                        Notice.popRegisteredSuccessMessage(result);
                    }
                } else {
                    Log.d("ResetPassword", "異常");
                }
            } else if (mContext instanceof ForgetPasswordActivity) {
                if (qType == CommandType.account_send_setting_password_email) {
                    Notice.setHandler((ForgetPasswordActivity) mContext);
                    if (result == "-2") {
                        Notice.popForgetPasswordErrorMessageByEmailDoesNotExist();
                    } else {
                        Notice.popForgetPasswordSuccessMessage(result);
                    }
                } else {
                    Log.d("ForgetPasswordActivity", "異常");
                }
            } else {
                Log.e(TAG, "onPostExecute success");
            }
        }
        parameters.clear();
        parameters = null;
    }

    private String dispatchUrl(CommandType type) {
        String url = null;
        switch (type) {
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
            case student_get_news:
                Log.e(TAG, "dipatchURL");
                url = GDefine.student_get_news;
                break;
            case student_get_calculation_result:
                url = GDefine.student_get_calculation_result;
                break;
            case teacher_get_calculation_result:
                url = GDefine.teacher_get_calculation_result;
                break;
            case student_get_result_list:
                url = GDefine.student_get_result_list;
                break;
            case teacher_get_result_list:
                url = GDefine.teacher_get_result_list;
                break;
            case teacher_request_record:
                url = GDefine.teacher_request_record;
                break;
            case student_modify_user_info:
                url = GDefine.student_modify_user_info;
                break;
            case student_get_evaluation_request_evaluation:
                url = GDefine.student_get_evaluation_request_evaluation;
                break;
            case teacher_modify_user_info:
                url = GDefine.teacher_modify_user_info;
                break;
            case student_get_request_list:
                url = GDefine.student_get_evaluation_list;
                break;
            case student_get_request_setting:
                url = GDefine.student_get_request_setting;
                break;
            case student_get_evaluation_record:
                url = GDefine.student_get_evaluation_record;
                break;
            case teacher_request_list:
                url = GDefine.teacher_request_list;
                break;
            case student_user_logout:
                url = GDefine.student_user_logout;
                break;
            case student_evaluation_fill_evaluation_info:
                url = GDefine.student_evaluation_fill_evaluation_info;
                break;
            case teacher_add_evaluation_record:
                url = GDefine.teacher_add_evaluation_record;
                break;
            case teacher_get_news:
                url = GDefine.teacher_get_news;
                break;
            case teacher_user_logout:
                url = GDefine.teacher_user_logout;
                break;
            case transfer_wav_to_text_by_based64:
                url=GDefine.teacher_transfer_wav_to_text_by_based64;
                Log.e(TAG,"case :"+url);
                break;
            case teacher_evaluation_reject_request:
                url=GDefine.teacher_evaluation_reject_request;
                Log.e(TAG,"case :"+url);
                break;
            default:
                url = "*****";
                break;
        }
        return url;
    }

    @Override
    public void initJSONObject(Map<String, String> map) {
        // TODO
        Log.i("NetTask", "initJSONObject");
        this.parameters = map;
    }
}
