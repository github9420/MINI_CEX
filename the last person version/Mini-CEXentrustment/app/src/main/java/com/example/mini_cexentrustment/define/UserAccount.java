package com.example.mini_cexentrustment.define;

import java.io.Serializable;

/**
 * Created by 信威 on 2017/10/23.
 */
@SuppressWarnings("serial")
public class UserAccount implements Serializable {

    private long id;
    private String userId = ""; //權限	system:系統管理者    standard:一般    trail:試用
    private String userAuth = ""; //權限	system:系統管理者    standard:一般    trail:試用
    private String userType = ""; //身份別	manager:管理者    member:成員
    private String email = ""; //email
    private String userName = ""; //姓名
    private Integer groupSNo = 0; //團體編號
    private String groupName = ""; // 團體名稱
    private String LoginRole = "";  //目前登入角色
    private String mobilePhone =""; //手機號碼
    private String phone = ""; //電話號碼
    private String phoneEx = ""; //分機號碼
    private String token= ""; // 身分驗證碼
    private String needChangePassword= "true"; //true:需要立即更改密碼  false:正常登入
    private String create_date = "";  //建檔日期 (yyyyMMdd hh:mm:ss)


    public UserAccount(){}

    public UserAccount(long id, String userId, String userAuth, String userType, String email, String userName, Integer groupSNo, String groupName, String LoginRole, String mobilePhone, String phone, String phoneEx, String token, String needChangePassword, String create_date){
        this.id = id;
        this.userId = userId;
        this.userAuth = userAuth;
        this.userType = userType;
        this.email = email;
        this.userName = userName;
        this.groupSNo = groupSNo;
        this.groupName = groupName;
        this.LoginRole = LoginRole;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.phoneEx =phoneEx;
        this.token = token;
        this.needChangePassword = needChangePassword;
        this.create_date = create_date;
    }

    public void setId(long value){
        id = value;
    }
    public long getId(){
        return  id;
    }

    public void setUserId(String value){
        userId = value;
    }
    public String getUserId(){
        return  userId;
    }

    public void setUserAuth(String value){
        userAuth = value;
    }
    public String getUserAuth(){
        return  userAuth;
    }

    public void setUserType(String value){
        userType = value;
    }
    public String getUserType(){
        return  userType;
    }

    public void setEmail(String value){
        email = value;
    }
    public String getEmail(){
        return  email;
    }

    public void setUserName(String value){
        userName = value;
    }
    public String getUserName(){
        return  userName;
    }

    public void setGroupSNo(Integer value){
        groupSNo = value;
    }
    public Integer getGroupSNo(){
        return  groupSNo;
    }

    public void setGroupName(String value){
        groupName = value;
    }
    public String getGroupName(){
        return  groupName;
    }

    public void setLoginRole(String value){
        LoginRole = value;
    }
    public String getLoginRole(){
        return  LoginRole;
    }

    public void setMobilePhone(String value){
        mobilePhone = value;
    }
    public String getMobilePhone(){
        return  mobilePhone;
    }

    public void setPhone(String value){
        phone = value;
    }
    public String getPhone(){
        return  phone;
    }

    public void setPhoneEX(String value){
        phoneEx = value;
    }
    public String getPhoneEX(){
        return  phoneEx;
    }

    public void setToken(String value){
        token = value;
    }
    public String getToken(){
        return  token;
    }

    public void setNeedChangePassword(String value){
        needChangePassword = value;
    }
    public String getNeedChangePassword(){
        return  needChangePassword;
    }

    public void setCreateDate(String value){
        create_date = value;
    }
    public String getCreateDate() {
        return create_date;
    }
}
