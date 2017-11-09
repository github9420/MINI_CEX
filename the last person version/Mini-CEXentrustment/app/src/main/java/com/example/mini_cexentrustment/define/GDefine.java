package com.example.mini_cexentrustment.define;

public final class GDefine {

	public final static String encode = "utf-8"; //編碼

	/**
	 * POST method
	 */
	public final static String host = "220.133.185.190"; //網址
	public final static String url = "http://220.133.185.190"; //網址
	public final static String account_user_authentication = url + "account/user-authentication"; // 使用者身份驗證(release platform)

	public final static String account_user_login = url + "account/user-login"; // 登入(release platform)

	public final static String account_user_sign_up = url + "account/user-sign-up"; // 註冊(release platform)
	public final static String account_send_setting_password_email = url + "account/send-setting-password-email"; // 寄送重設密碼信件(release platform)
	public final static String account_modify_user_password = url + "account/modify-user-password"; // 重設密碼(release platform)

	public final static String modify_login_role = url + "account/modify-login-role"; // 設定身份別(release platform)
	public final static String account_get_user_info = url + "account/get-user-info"; // 取得帳號基本資料(release platform)

	/**
	 * DB paramter
	 */
	public final static String DATABASE_NAME = "miniCEX.db"; //資料庫名稱
	public final static int DATABASE_VERSION = 1; //資料庫版本，資料結構改變的時候要更這個數字，通常加 1;
	public final static String TABLE_NAME_USERACCOUNT = "useraccount";
}
