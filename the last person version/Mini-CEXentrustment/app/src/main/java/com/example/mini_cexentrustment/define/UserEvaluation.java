package com.example.mini_cexentrustment.define;

import java.io.Serializable;

/**
 * Created by 信威 on 2017/10/23.
 */
@SuppressWarnings("serial")
public class UserEvaluation implements Serializable {

    private long id;
    private String documentSNo = ""; //權限	system:系統管理者    standard:一般    trail:試用
    private String item1_1 = ""; //權限	system:系統管理者    standard:一般    trail:試用
    private String item1_2 = ""; //身份別	manager:管理者    member:成員
    private String item2_1 = ""; //email
    private String item2_2 = ""; //姓名
    private String item2_3 = ""; //團體編號
    private String item2_4 = ""; // 團體名稱
    private String item2_5 = "";  //目前登入角色
    private String item2_6 =""; //手機號碼
    private String comment = ""; //電話號碼
    private String evaluateDateTime = ""; //分機號碼


    public UserEvaluation(){}

    public UserEvaluation(long id, String userId, String userAuth, String userType, String email, String userName, Integer groupSNo, String groupName, String LoginRole, String mobilePhone, String phone, String phoneEx, String token, String needChangePassword, String create_date){
        this.id = id;
        this.documentSNo = documentSNo;
        this.item1_1 = item1_1;
        this.item1_2 = item1_2;
        this.item2_1 = item2_1;
        this.item2_2 = item2_2;
        this.item2_3 = item2_3;
        this.item2_4 = item2_4;
        this.item2_5 = item2_5;
        this.item2_6 = item2_6;
        this.comment = comment;
        this.evaluateDateTime =evaluateDateTime;
    }

    public void setId(long value){
        id = value;
    }
    public long getId(){
        return  id;
    }

    public void setdocumentSNo(String value){
        documentSNo = value;
    }
    public String getdocumentSNo(){
        return  documentSNo;
    }

    public void setitem1_1(String value){
        item1_1 = value;
    }
    public String getitem1_1(){
        return  item1_1;
    }

    public void setitem1_2(String value){
        item1_2 = value;
    }
    public String getitem1_2(){
        return  item1_2;
    }

    public void setitem2_1(String value){
        item2_1 = value;
    }
    public String getitem2_1(){
        return  item2_1;
    }

    public void setitem2_2(String value){
        item2_2 = value;
    }
    public String getitem2_2(){
        return  item2_2;
    }

    public void setitem2_3(String value){
        item2_3 = value;
    }
    public String getitem2_3(){
        return  item2_3;
    }

    public void setitem2_4(String value){
        item2_4 = value;
    }
    public String getitem2_4(){
        return  item2_4;
    }

    public void setitem2_5(String value){
        item2_5 = value;
    }
    public String getitem2_5(){
        return  item2_5;
    }

    public void setitem2_6(String value){
        item2_6 = value;
    }
    public String getitem2_6(){
        return  item2_6;
    }

    public void setcomment(String value){
        comment = value;
    }
    public String getcomment(){
        return  comment;
    }

    public void setevaluateDateTime(String value){
        evaluateDateTime = value;
    }
    public String getevaluateDateTime(){
        return  evaluateDateTime;
    }
}
