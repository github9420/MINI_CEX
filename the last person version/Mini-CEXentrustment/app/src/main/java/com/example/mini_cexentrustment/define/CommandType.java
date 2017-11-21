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


    student_get_news,                      //學生取得新聞
    student_get_calculation_result,         //學生查詢統計結果
    teacher_get_calculation_result,          //老師查詢統計結果
    student_get_result_list,                //學生查詢明細清單
    teacher_get_result_list,               //老師查詢明細清單
    teacher_request_list,
    student_get_request_setting,
    student_get_request_list,
    teacher_request_record,
    student_modify_user_info,
    teacher_modify_user_info,
    student_get_evaluation_request_evaluation,
    student_evaluation_fill_evaluation_info,
    student_get_evaluation_record,
    student_user_logout,
    teacher_user_logout
}
