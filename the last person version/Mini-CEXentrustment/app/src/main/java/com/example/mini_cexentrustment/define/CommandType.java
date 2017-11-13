package com.example.mini_cexentrustment.define;

/**
 * Created by 信威 on 2017/8/3.
 */
public enum CommandType {
    account_user_authentication,              //使用者身份驗證
    account_user_login,                     //登入
    account_user_sign_up,                   //註冊
    account_send_setting_password_email,      //寄送重設密碼信件
    account_modify_user_password,           //重設密碼
    modify_login_role,                     //設定身份別
    account_get_user_info,                  // 取得帳號基本資料

}
