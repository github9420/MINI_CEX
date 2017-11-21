package com.example.mini_cexentrustment.define;

public final class GDefine {

	public final static String encode = "utf-8"; //編碼

	/**
	 * POST method
	 */
	public final static String host = "http://minicexentrustment.azurewebsites.net/"; //網址
	//public final static String url = "http://minicexentrustment.azurewebsites.net/api/"; //網址
	public final static String url = "http://140.128.68.202/api/"; //網址
	public final static String account_user_authentication = url + "account/user-authentication"; // 使用者身份驗證(release platform)

	public final static String account_user_login = url + "account/user-login"; // 登入(release platform)

	public final static String account_user_sign_up = url + "account/user-sign-up"; // 註冊(release platform)
	public final static String account_send_setting_password_email = url + "account/send-setting-password-email"; // 寄送重設密碼信件(release platform)
	public final static String account_modify_user_password = url + "account/modify-user-password"; // 重設密碼(release platform)

	public final static String modify_login_role = url + "account/modify-login-role"; // 設定身份別(release platform)
	public final static String account_get_user_info = url + "account/get-user-info"; // 取得帳號基本資料(release platform)


	public final static String student_get_news = url + "student/news/get-news";
	public final static String student_get_calculation_result=url+"student/search/get-calculation-result";
	public final static String teacher_get_calculation_result=url+"teacher/search/get-calculation-result";
	public final static String student_get_result_list=url+"student/search/get-result-list";
	public final static String teacher_get_result_list=url+"teacher/search/get-result-list";
	//public final static String student_get_evaluation_list = url + "student/evaluation/get-evaluation-list";
	public final static String student_get_evaluation_setting = url + "student/evaluation/get-setting";
	public final static String student_get_request_setting = url + "student/evaluation/get-request-setting";
	public final static String student_modify_user_info = url + "student/setting/modify-user-info";
	public final static String teacher_modify_user_info = url + "teacher/setting/modify-user-info";
	public final static String student_user_logout=url+"student/setting/user-logout";
	public final static String teacher_user_logout=url+"teacher/setting/user-logout";
	public final static String student_get_evaluation_list = url + "student/evaluation/get-evaluation-list";
	public final static String student_get_evaluation_record = url + "student/evaluation/get-evaluation-record";
	public final static String student_evaluation_fill_evaluation_info = url + "student/evaluation/fill-evaluation-info";
	public final static String student_get_evaluation_request_evaluation = url + "student/evaluation/request-evaluation";
	//Teacher
	public final static String teacher_request_list = url + "teacher/evaluation/get-request-list";
	public final static String teacher_request_record = url + "teacher/evaluation/get-request-record";

	/**
	 * DB paramter
	 */
	public final static String DATABASE_NAME = "miniCEX.db"; //資料庫名稱
	public final static int DATABASE_VERSION = 1; //資料庫版本，資料結構改變的時候要更這個數字，通常加 1;
	public final static String TABLE_NAME_USERACCOUNT = "useraccount";
	public final static String TABLE_NAME_News = "StudentNews";
}
