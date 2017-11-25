package com.example.mini_cexentrustment.tw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/20.
 */

public class Fragment_setup_editprofile extends Fragment implements View.OnClickListener {
    private EditText edt_setup_email;
    private EditText edt_setup_cellphone;
    private EditText edt_setup_phone;
    private EditText edt_setup_exphone;

    private EditText edt_setup_new_password;
    private EditText edt_setup_new_confirmpassword;

    private Button btn_setup_back;
    private Button btn_setup_confirm;

    private String userType="";
    private String userName="";
    private String userId="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_editprofile, container, false);
        edt_setup_email = (EditText) view.findViewById(R.id.id_setup_email);
        edt_setup_cellphone = (EditText) view.findViewById(R.id.id_setup_cellphone);
        edt_setup_phone = (EditText) view.findViewById(R.id.id_setup_phone);
        edt_setup_exphone = (EditText) view.findViewById(R.id.id_setup_exphone);

        edt_setup_new_password = (EditText) view.findViewById(R.id.id_setup_email);
        edt_setup_new_confirmpassword = (EditText) view.findViewById(R.id.id_setup_email);

        btn_setup_back = (Button) view.findViewById(R.id.setup_editprofile_back);
        btn_setup_confirm = (Button) view.findViewById(R.id.id_btn_setup_profile_confirm);

        btn_setup_back.setOnClickListener(this);
        btn_setup_confirm.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.setup_editprofile_back:
                Fragment_setup fse = new Fragment_setup();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_content, fse, "profiletosetup");
                tx.addToBackStack(null);
                //tx.hide(this);
                tx.remove(this);
                tx.commit();

                break;
            case R.id.id_btn_setup_profile_confirm:

                // Check for a valid password, if the user entered one.
                int count=0;
                if (edt_setup_email != null && !edt_setup_email.equals("") ) {
                    count++;
                }else{
                    Toast.makeText(getActivity(),"信箱不能為空", Toast.LENGTH_SHORT).show();
                }
                if (edt_setup_cellphone != null && !edt_setup_cellphone.equals("") ) {
                    count++;
                }else{
                    Toast.makeText(getActivity(),"手機號碼不能為空", Toast.LENGTH_SHORT).show();
                }
                if (edt_setup_phone != null && !edt_setup_phone.equals("") ) {
                    count++;
                }else{
                    Toast.makeText(getActivity(),"室內電話號碼不能為空", Toast.LENGTH_SHORT).show();
                }


                if(count==3){
                    UserAccountDAO db_data = new UserAccountDAO(getActivity());
                    List<UserAccount> items = db_data.getAll();
                    for (UserAccount i : items) {
                        userType = String.valueOf(i.getLoginRole()).toString();
                        userName = String.valueOf(i.getUserName()).toString();
                        userId = String.valueOf(i.getUserId()).toString();
                    }

                    if(userType.equals("teacher")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userId",userId);
                        map.put("userName", userName);
                        map.put("email",edt_setup_email.getText().toString());
                        map.put("mobilePhone",edt_setup_cellphone.getText().toString());
                        map.put("phone",edt_setup_phone.getText().toString());
                        map.put("phoneEX",edt_setup_exphone.getText().toString());
                        map.put("userPassword","");
                        NetTask netTask =  new NetTask();
                        netTask.initJSONObject(map);
                        netTask.setCommandType(CommandType.teacher_modify_user_info);
                        netTask.setActiveContext(getActivity());
                        netTask.execute();

                        while(!ServerConnect.F_setting_flag){

                        }
                    }else{
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userId",userId);
                        map.put("userName", userName);
                        map.put("email",edt_setup_email.getText().toString());
                        map.put("mobilePhone",edt_setup_cellphone.getText().toString());
                        map.put("phone",edt_setup_phone.getText().toString());
                        map.put("phoneEX",edt_setup_exphone.getText().toString());

                        NetTask netTask =  new NetTask();
                        netTask.initJSONObject(map);
                        netTask.setCommandType(CommandType.student_modify_user_info);
                        netTask.setActiveContext(getActivity());
                        netTask.execute();

                        while(!ServerConnect.F_setting_flag){

                        }
                    }


                    if(ServerConnect.setting_imformation){

                        Fragment_setup fse2 = new Fragment_setup();

                        FragmentManager fm2 = getFragmentManager();
                        FragmentTransaction tx2 = fm2.beginTransaction();
                        tx2.replace(R.id.id_content, fse2, "profiletosetup");
                        //tx2.addToBackStack(null);
                        //tx2.hide(this);
                        tx2.commit();
                        tx2.remove(this);
                        Toast.makeText(getActivity(),"修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"修改失敗", Toast.LENGTH_SHORT).show();
                    }
                    ServerConnect.F_setting_flag=false;
                    ServerConnect.setting_imformation=false;

                }

                // Check for a valid email address.


                break;
            default:

                break;
        }
    }

}
